package com.batutinhas.spock.demo.mapper.impl

import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.batutinhas.spock.demo.mapper.GameMapper
import spock.lang.Specification
import spock.lang.Subject

import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class GameMapperImplSpec extends Specification {

    @Subject
    private GameMapper gameMapper

    private Clock clock = Clock.fixed(Instant.parse("1996-01-20T08:30:00.00Z"), ZoneOffset.UTC)

    def setup() {
        gameMapper = new GameMapperImpl(clock)
    }

    def "Deve transformar o retorno da API em uma lista de Game"() {
        given: "Um SearchResponse"
        def searchResponse = SearchResponseFixture.getOneValid()

        when: "Invocar mapSearchResponse"
        def gameSearch = gameMapper.mapSearchResponse(searchResponse)

        then: "Deve transformar o retorno da API em uma lista de Game"
        gameSearch.games.size() == 1
        verifyAll(gameSearch.games[0]) {
            name == "Starfox"
            description == "Um jogo bacana"
            coverImage == "url da imagem"
            originalReleaseDate == "1997-04-27"
        }
        gameSearch.requestDateTime == "20-01-1996 08:30:00"
    }

    def "Caso o retorno da API seja vazio, deve retornar uma lista vazia de Game"() {
        given: "Um SearchResponse sem resultados"
        def emptySearchResponse = SearchResponseFixture.getOneEmpty()

        when: "Invocar mapSearchResponse"
        def gameSearch = gameMapper.mapSearchResponse(emptySearchResponse)

        then: "Deve retornar uma lista vazia de Game"
        gameSearch.games.isEmpty()
    }
}
