package com.batutinhas.spock.demo.usecase.impl


import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.GameFixture
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.batutinhas.spock.demo.mapper.GameMapper
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase
import spock.lang.Specification
import spock.lang.Subject

class SearchGamesUseCaseImplSpec extends Specification {

    @Subject
    private SearchGamesUseCase searchGamesUseCase

    private SearchGamesGateway searchGamesGateway = Mock()
    private GameMapper gameMapper = Mock()

    def setup() {
        searchGamesUseCase = new SearchGamesUseCaseImpl(searchGamesGateway, gameMapper);
    }

    def "Deve buscar uma lista de jogos na API da Giant Bomb e trazer uma lista de Game"() {
        given: "Um nome de jogo"
        String query = "Starfox"

        and: "Uma resposta válida da API"
        def searchResponse = SearchResponseFixture.getOneValid()
        1 * searchGamesGateway.searchGames(query) >> searchResponse

        and: "O mapeamento do retorno da API para uma lista de Game"
        def game = GameFixture.getOneValid()
        1 * gameMapper.mapSearchResponse(searchResponse) >> [game]

        when: "Invocar execute"
        def games = searchGamesUseCase.execute(query)

        then: "Deve trazer uma lista de Game"
        games.size() == 1
        verifyAll(games[0]) {
            name == "Starfox"
            description == "Um jogo bacana"
            coverImage == "url da imagem"
            originalReleaseDate == "1997-04-27"
        }
    }
}
