package com.batutinhas.spock.demo.mapper.impl


import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.batutinhas.spock.demo.mapper.GameMapper
import spock.lang.Specification

class GameMapperImplSpec extends Specification {

    GameMapper gameMapper

    def setup() {
        gameMapper = new GameMapperImpl()
    }

    def "O retorno da Giant Bomb API deve ser transformado em uma lista de Game"() {
        given: "Um SearchResponse da API"
        def searchResponse = SearchResponseFixture.getOneValid()

        when: "Invocar mapSearchResponse"
        def games = gameMapper.mapSearchResponse(searchResponse)

        then: "Deve transformar os resultados em uma lista de Game"
        games.size() == 1
        verifyAll(games[0]) {
            name == "Starfox"
            description == "Um jogo bacana"
            coverImage == "url da imagem"
            originalReleaseDate == "1997-04-27"
        }
    }

    def "Um retorno vazio da Giant Bomb API deve retornar também uma lista vazia de Game"() {
        given: "Um SearchResponse vazia da API"
        def emptySearchResponse = SearchResponseFixture.getOneEmpty()

        when: "Invocar mapSearchResponse"
        def games = gameMapper.mapSearchResponse(emptySearchResponse)

        then: "Deve transformar também uma lista vazia de Game"
        games.isEmpty()
    }
}
