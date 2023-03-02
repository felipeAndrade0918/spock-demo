package com.batutinhas.spock.demo.controller

import com.batutinhas.spock.demo.domain.Game
import com.fasterxml.jackson.core.type.TypeReference
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
        List<Game> games = new ObjectMapper().readValue(contentAsString, new TypeReference<List<Game>>(){})
        games.size() == 1
        verifyAll(games[0]) {
            name == "Star Fox 64"
            description == "Return to the cockpit of the Arwing fighter craft and save the Lylat system from conquest in this re-telling of the original Star Fox."
            coverImage == "https://www.giantbomb.com/a/uploads/original/8/82063/2836282-sf64.jpg"
            originalReleaseDate == "1997-04-27"
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
        List<Game> games = new ObjectMapper().readValue(contentAsString, new TypeReference<List<Game>>(){})
        games.isEmpty()
    }
}
