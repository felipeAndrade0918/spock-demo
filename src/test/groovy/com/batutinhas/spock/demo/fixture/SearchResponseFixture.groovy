package com.batutinhas.spock.demo.fixture

import com.batutinhas.spock.demo.external.dto.GameImageResponse
import com.batutinhas.spock.demo.external.dto.GameResponse
import com.batutinhas.spock.demo.external.dto.SearchResponse

class SearchResponseFixture {

    static SearchResponse getMock() {
        def gameResponse = GameResponse.builder()
                .name("Starfox")
                .deck("Um jogo bacana")
                .originalReleaseDate("1997-04-27")
                .image(GameImageResponse.builder()
                        .originalUrl("url da imagem")
                        .build())
                .build()
        return SearchResponse.builder().results([gameResponse]).build()
    }
}
