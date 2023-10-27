package com.batutinhas.spock.demo.fixture;

import com.batutinhas.spock.demo.external.dto.GameImageResponse;
import com.batutinhas.spock.demo.external.dto.GameResponse;
import com.batutinhas.spock.demo.external.dto.SearchResponse;

import java.util.List;

public class SearchResponseTemplate {

    public static SearchResponse getOneValid() {
        GameResponse gameResponse = GameResponse.builder()
                .name("Starfox")
                .deck("Um jogo bacana")
                .originalReleaseDate("1997-04-27")
                .image(GameImageResponse.builder()
                        .originalUrl("url da imagem")
                        .build())
                .build();
        return SearchResponse.builder().results(List.of(gameResponse)).build();
    }

    public static SearchResponse getOneEmpty() {
        return SearchResponse.builder().results(List.of()).build();
    }
}
