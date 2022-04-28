package com.batutinhas.spock.demo.controller


import com.batutinhas.spock.demo.fixture.GameFixture
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase
import spock.lang.Specification
import spock.lang.Subject

class SearchGamesControllerSpec extends Specification {

    @Subject
    SearchGamesController searchGamesController

    SearchGamesUseCase searchGamesUseCase = Mock()

    def setup() {
        searchGamesController = new SearchGamesController(searchGamesUseCase)
    }

    def "O endpoint principal deve retornar uma lista de Game baseado no nome de um jogo"() {
        given: "O nome de um jogo"
        String query = "Starfox"

        and: "Resposta válida da API"
        def game = GameFixture.getOneValid()
        1 * searchGamesUseCase.execute(query) >> [game]

        when: "Invocar searchGames"
        def games = searchGamesController.searchGames(query)

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
