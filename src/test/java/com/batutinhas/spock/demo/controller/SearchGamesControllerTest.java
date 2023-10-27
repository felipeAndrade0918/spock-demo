package com.batutinhas.spock.demo.controller;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.fixture.GameSearchTemplate;
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SearchGamesControllerTest {

    private SearchGamesController searchGamesController;

    private final SearchGamesUseCase searchGamesUseCase = Mockito.mock(SearchGamesUseCase.class);

    @BeforeEach
    public void setup() {
        searchGamesController = new SearchGamesController(searchGamesUseCase);
    }

    @Test
    @DisplayName("O endpoint principal deve retornar uma lista de Game baseado no nome de um jogo")
    void shouldSearchGames() {
        // given
        String query = "Starfox";

        // Resposta válida da API
        GameSearch gameSearchFixture = GameSearchTemplate.getOne();
        Mockito.when(searchGamesUseCase.execute(query)).thenReturn(gameSearchFixture);

        // when
        GameSearch gameSearch = searchGamesController.searchGames(query);

        // then
        Assertions.assertEquals(1, gameSearch.getGames().size());
        Assertions.assertEquals("20-01-1996 08:30:00", gameSearch.getRequestDateTime());

        Game game = gameSearch.getGames().get(0);
        Assertions.assertEquals("Starfox", game.getName());
        Assertions.assertEquals("Um jogo bacana", game.getDescription());
        Assertions.assertEquals("url da imagem", game.getCoverImage());
        Assertions.assertEquals("1997-04-27", game.getOriginalReleaseDate());

        Mockito.verify(searchGamesUseCase, Mockito.times(1)).execute(query);
    }
}