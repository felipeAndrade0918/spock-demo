package com.batutinhas.spock.demo.controller

import com.batutinhas.spock.demo.domain.Game
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
class SearchGamesControllerIntegrationSpec extends Specification {

    @SpringBean
    private SearchGamesGateway searchGamesGateway = Mock()

    @Autowired
    MockMvc mockMvc;

    def "O endpoint principal deve retornar uma lista de Game baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Starfox"

        and: "Resposta válida da API"
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
        List<Game> games = new ObjectMapper().readValue(contentAsString, new TypeReference<List<Game>>(){})
        games.size() == 1
        verifyAll(games[0]) {
            name == "Starfox"
            description == "Um jogo bacana"
            coverImage == "url da imagem"
            originalReleaseDate == "1997-04-27"
        }
    }
}
