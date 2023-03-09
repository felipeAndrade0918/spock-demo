package com.batutinhas.spock.demo.controller

import com.batutinhas.spock.demo.domain.Game
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.WireMockSpring
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "local")
class SearchGamesControllerComponentSpec extends Specification {

    @Shared
    private WireMockServer wireMock = new WireMockServer(WireMockSpring.options()
            .port(8888)
            .usingFilesUnderDirectory("config/wiremock/mocks"))

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper objectMapper = new ObjectMapper()

    def setupSpec() {
        wireMock.start()
    }

    def cleanupSpec() {
        wireMock.stop()
    }

    def "O endpoint principal deve retornar uma lista de jogos baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Star Fox 64"

        when: "Invocar o endpoint"
        def mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/search")
                        .queryParam("query", query))
                .andReturn()

        then: "Deve responder com status 200"
        mvcResult.getResponse().status == 200

        and: "Retornar uma lista de jogos"
        def contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)
        def jsonNode = objectMapper.readTree(contentAsString)
        jsonNode.get("games").size() == 1
        verifyAll(jsonNode.get("games")[0]) {
            it.get("name").asText() == "Star Fox 64"
            it.get("description").asText() == "Return to the cockpit of the Arwing fighter craft and save the Lylat system from conquest in this re-telling of the original Star Fox."
            it.get("coverImage").asText() == "https://www.giantbomb.com/a/uploads/original/8/82063/2836282-sf64.jpg"
            it.get("originalReleaseDate").asText() == "1997-04-27"
        }
    }

    def "O endpoint principal deve retornar uma lista vazia de jogos caso não encontre resultados"() {
        given: "O nome de um jogo que não existe"
        String query = "Coxicas do Espaço - O Resgate"

        when: "Invocar o endpoint"
        def mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/search")
                        .queryParam("query", query))
                .andReturn()

        then: "Deve responder com status 200"
        mvcResult.getResponse().status == 200

        and: "Retornar uma lista vazia de jogos"
        def contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)
        def jsonNode = objectMapper.readTree(contentAsString)
        jsonNode.get("games").isEmpty()
    }
}
