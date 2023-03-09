package com.batutinhas.spock.demo.controller


import com.batutinhas.spock.demo.fixture.GameSearchFixture
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase
import spock.lang.Specification
import spock.lang.Subject

class SearchGamesControllerSpec extends Specification {

    @Subject
    private SearchGamesController searchGamesController

    private SearchGamesUseCase searchGamesUseCase = Mock()

    def setup() {
        searchGamesController = new SearchGamesController(searchGamesUseCase)
    }

    def "O endpoint principal deve retornar uma lista de Game baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Starfox"

        and: "Resposta válida da API"
        def gameSearchFixture = GameSearchFixture.getOne()
        1 * searchGamesUseCase.execute(query) >> gameSearchFixture

        when: "Invocar searchGames"
        def gameSearch = searchGamesController.searchGames(query)

        then: "Deve trazer uma lista de Game"
        gameSearch.games.size() == 1
        verifyAll(gameSearch.games[0]) {
            name == "Starfox"
            description == "Um jogo bacana"
            coverImage == "url da imagem"
            originalReleaseDate == "1997-04-27"
        }
        gameSearch.requestDateTime == "20-01-1996 08:30:00"
    }
}
