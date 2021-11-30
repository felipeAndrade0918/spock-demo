package com.batutinhas.spock.demo.usecase.impl

import com.batutinhas.spock.demo.domain.Game
import com.batutinhas.spock.demo.external.dto.SearchResponse
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.GameFixture
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.batutinhas.spock.demo.mapper.GameMapper
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase
import spock.lang.Specification

class SearchGamesUseCaseImplSpec extends Specification {

    SearchGamesUseCase searchGamesUseCase

    SearchGamesGateway searchGamesGateway = Mock()
    GameMapper gameMapper = Mock()

    def setup() {
        searchGamesUseCase = new SearchGamesUseCaseImpl(searchGamesGateway, gameMapper);
    }

    def "Deve buscar uma lista de jogos na API da Giant Bomb e trazer uma lista de Game"() {
        given: "Um nome de jogo"
        String query = "Starfox"

        and: "Resposta válida da API"
        def searchResponse = SearchResponseFixture.getMock()
        def game = GameFixture.getMock()
        1 * searchGamesGateway.searchGames(query) >> searchResponse
        1 * gameMapper.mapSearchResponse(searchResponse) >> [game]

        when: "Invocar execute"
        def games = searchGamesUseCase.execute(query)

        then: "Deve trazer uma lista de Game"
        games
        games.size() == 1
        games[0] == game
    }
}
