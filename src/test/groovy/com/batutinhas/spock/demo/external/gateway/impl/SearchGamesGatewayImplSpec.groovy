package com.batutinhas.spock.demo.external.gateway.impl

import com.batutinhas.spock.demo.external.client.GiantBombClient
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import spock.lang.Specification

class SearchGamesGatewayImplSpec extends Specification {

    private SearchGamesGateway searchGamesGateway

    private GiantBombClient giantBombClient = Mock()

    def setup() {
        searchGamesGateway = new SearchGamesGatewayImpl(giantBombClient)
    }

    def "Deve buscar uma lista de jogo na API"() {
        given: "Um nome de um jogo"
        String query = "Starfox"

        and: "Uma chamada para a API"
        def searchResponseFixture = SearchResponseFixture.getOneValid()
        1 * giantBombClient.searchGames(query) >> searchResponseFixture

        when: "Invocar searchGames"
        def searchResponse = searchGamesGateway.searchGames(query)

        then: "Deve retornar um SearchResponse com a lista de resultados"
        searchResponse
        searchResponse.results.size() == 1

        verifyAll(searchResponse.results[0]) {
            it.name == "Starfox"
            deck == "Um jogo bacana"
            image
            image.originalUrl == "url da imagem"
        }
    }
}
