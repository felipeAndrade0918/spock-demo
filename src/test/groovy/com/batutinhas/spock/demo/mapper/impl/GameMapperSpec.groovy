package com.batutinhas.spock.demo.mapper.impl

import com.batutinhas.spock.demo.domain.Game
import com.batutinhas.spock.demo.external.dto.GameImageResponse
import com.batutinhas.spock.demo.external.dto.GameResponse
import com.batutinhas.spock.demo.external.dto.SearchResponse
import com.batutinhas.spock.demo.fixture.SearchResponseFixture
import com.batutinhas.spock.demo.mapper.GameMapper
import com.batutinhas.spock.demo.mapper.impl.GameMapperImpl
import spock.lang.Specification

class GameMapperSpec extends Specification {

    GameMapper gameMapper

    def setup() {
        gameMapper = new GameMapperImpl()
    }

    def "O retorno da Giant Bomb API deve ser transformado em uma lista de Game"() {
        given: "Um SearchResponse da API"
        def searchResponse = SearchResponseFixture.getMock()

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
}
