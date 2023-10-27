package com.batutinhas.spock.demo.mapper.impl;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.fixture.SearchResponseTemplate;
import com.batutinhas.spock.demo.mapper.GameMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

class GameMapperImplTest {

    private GameMapper gameMapper;
    private final Clock clock = Clock.fixed(Instant.parse("1996-01-20T08:30:00.00Z"), ZoneOffset.UTC);

    @BeforeEach
    public void setup() {
        gameMapper = new GameMapperImpl(clock);
    }

    @Test
    @DisplayName("Deve transformar o retorno da API em uma lista de Game")
    void shouldMapApiResponseToGameList() {
        // given
        SearchResponse searchResponse = SearchResponseTemplate.getOneValid();

        // when
        GameSearch gameSearch = gameMapper.mapSearchResponse(searchResponse);

        // then
        Assertions.assertEquals(1, gameSearch.getGames().size());
        Assertions.assertEquals("20-01-1996 08:30:00", gameSearch.getRequestDateTime());

        Game game = gameSearch.getGames().get(0);
        Assertions.assertEquals("Starfox", game.getName());
        Assertions.assertEquals("Um jogo bacana", game.getDescription());
        Assertions.assertEquals("url da imagem", game.getCoverImage());
        Assertions.assertEquals("1997-04-27", game.getOriginalReleaseDate());
    }

    @Test
    @DisplayName("Caso o retorno da API seja vazio, deve retornar uma lista vazia de Game")
    void shouldMapEmptyApiResponseToGameList() {
        // given
        SearchResponse searchResponse = SearchResponseTemplate.getOneEmpty();

        // when
        GameSearch gameSearch = gameMapper.mapSearchResponse(searchResponse);

        // then
        Assertions.assertTrue(gameSearch.getGames().isEmpty());
    }
}