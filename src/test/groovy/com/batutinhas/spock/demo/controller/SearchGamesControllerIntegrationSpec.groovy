package com.batutinhas.spock.demo.controller

import com.batutinhas.spock.demo.domain.Game
import com.batutinhas.spock.demo.fixture.GameFixture
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase
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
    SearchGamesUseCase searchGamesUseCase = Mock()

    @Autowired
    MockMvc mockMvc;

    def "O endpoint principal deve retornar uma lista de Game baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Starfox"

        and: "Resposta válida da API"
        def game = GameFixture.getMock()
        1 * searchGamesUseCase.execute(query) >> [game]

        when: "Invocar searchGames"
        def mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/search")
                        .queryParam("query", query))
                .andReturn()

        then: "Deve trazer uma lista de Game"
        mvcResult.getResponse().status == 200
        def contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)
        List<Game> content = new ObjectMapper().readValue(contentAsString, new TypeReference<List<Game>>(){})
        content == [game]
    }
}
