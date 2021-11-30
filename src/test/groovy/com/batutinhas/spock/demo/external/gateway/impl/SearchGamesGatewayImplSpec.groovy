package com.batutinhas.spock.demo.external.gateway.impl

import com.batutinhas.spock.demo.external.client.GiantBombClient
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import spock.lang.Specification

class SearchGamesGatewayImplSpec extends Specification {

    SearchGamesGateway searchGamesGateway

    GiantBombClient giantBombClient = Mock()

    def setup() {
        searchGamesGateway = new SearchGamesGatewayImpl(giantBombClient)
    }

    def "Deve buscar jogos na API da Giant Bomb baseado no nome de um jogo"() {
        given: "Um nome de um jogo"
        String query = "Starfox"

        and: "Uma chamada válida a API"
        1 * giantBombClient.searchGames(query) >> SearchResponseFixture.getMock()

        when: "Invocar searchGames"
        def searchResponse = searchGamesGateway.searchGames(query)

        then: "Deve retornar uma resposta da API com a lista de jogos"
        searchResponse
        searchResponse.results.size() == 1
        verifyAll(searchResponse.results[0]) {
            name == "Starfox"
            deck == "Um jogo bacana"
            originalReleaseDate == "1997-04-27"
            image
            image.originalUrl == "url da imagem"
        }
    }
}
