package com.batutinhas.spock.demo.controller


import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@SpringBootTest
@AutoConfigureMockMvc
class SearchGamesControllerIntegrationSpec extends Specification {

    @SpringBean
    private SearchGamesGateway searchGamesGateway = Mock()

    @Autowired
    private MockMvc mockMvc

    @SpringBean
    private Clock clock = Clock.fixed(Instant.parse("1996-01-20T08:30:00.00Z"), ZoneOffset.UTC)

    private ObjectMapper objectMapper = new ObjectMapper()

    def "O endpoint principal deve retornar uma lista de jogos baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Starfox"

        and: "Uma resposta válida da API"
        def searchResponse = SearchResponseFixture.getOneValid()
        1 * searchGamesGateway.searchGames(query) >> searchResponse

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
            it.get("name").asText() == "Starfox"
            it.get("description").asText() == "Um jogo bacana"
            it.get("coverImage").asText() == "url da imagem"
            it.get("originalReleaseDate").asText() == "1997-04-27"
        }
        jsonNode.get("requestDateTime").asText() == "20-01-1996 08:30:00"
    }

    def "O endpoint principal deve retornar uma lista vazia de jogos caso não encontre resultados"() {
        given: "O nome de um jogo que não existe"
        String query = "Coxicas do Espaço - O Resgate"

        and: "Uma resposta vazia da API"
        def emptySearchResponse = SearchResponseFixture.getOneEmpty()
        1 * searchGamesGateway.searchGames(query) >> emptySearchResponse

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
