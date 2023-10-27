package com.batutinhas.spock.demo.external.gateway.impl;

import com.batutinhas.spock.demo.external.client.GiantBombClient;
import com.batutinhas.spock.demo.external.dto.GameResponse;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway;
import com.batutinhas.spock.demo.fixture.SearchResponseTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SearchGamesGatewayImplTest {

    private SearchGamesGateway searchGamesGateway;

    private final GiantBombClient giantBombClient = Mockito.mock(GiantBombClient.class);

    @BeforeEach
    public void setup() {
        searchGamesGateway = new SearchGamesGatewayImpl(giantBombClient);
    }

    @Test
    @DisplayName("Deve buscar uma lista de jogo na API")
    void shouldSearchGamesFromApi() {
        // given
        String query = "Starfox";

        // Uma chamada para a API
        SearchResponse searchResponseFixture = SearchResponseTemplate.getOneValid();
        Mockito.when(giantBombClient.searchGames(query)).thenReturn(searchResponseFixture);

        // when
        SearchResponse searchResponse = searchGamesGateway.searchGames(query);

        // then
        Assertions.assertNotNull(searchResponse);
        Assertions.assertEquals(1, searchResponse.getResults().size());

        GameResponse gameResponse = searchResponse.getResults().get(0);

        Assertions.assertEquals("Starfox", gameResponse.getName());
        Assertions.assertEquals("Um jogo bacana", gameResponse.getDeck());
        Assertions.assertEquals("url da imagem", gameResponse.getImage().getOriginalUrl());
        Assertions.assertEquals("1997-04-27", gameResponse.getOriginalReleaseDate());

        Mockito.verify(giantBombClient, Mockito.times(1)).searchGames(query);
    }
}