package com.batutinhas.spock.demo.usecase.impl;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway;
import com.batutinhas.spock.demo.fixture.GameSearchTemplate;
import com.batutinhas.spock.demo.fixture.SearchResponseTemplate;
import com.batutinhas.spock.demo.mapper.GameMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SearchGamesUseCaseImplTest {

    // Usando @InjectMocks todos as dependências com @Mock serão inseridas nessa instância
    // É necessário utilizar um classe concreta, interfaces não são possíveis com essa anotação
    @InjectMocks
    private SearchGamesUseCaseImpl searchGamesUseCase;

    @Mock
    private SearchGamesGateway searchGamesGateway;

    @Mock
    private GameMapper gameMapper;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        // Ao usar anotações do Mockito é necessário utilizar openMocks para inicializar as dependências
        // initMocks está deprecated!
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void after() throws Exception {
        // Ao usar openMocks é necessário fechá-lo
        autoCloseable.close();
    }

    @Test
    @DisplayName("Deve buscar uma lista de jogos na API da Giant Bomb e trazer uma lista de Game")
    void shouldFetchGamesAndMapThem() {
        // given
        String query = "Starfox";

        // Uma resposta válida da API
        SearchResponse searchResponseFixture = SearchResponseTemplate.getOneValid();
        Mockito.when(searchGamesGateway.searchGames(query)).thenReturn(searchResponseFixture);

        // O mapeamento do retorno da API para uma lista de Game
        GameSearch gameSearchFixture = GameSearchTemplate.getOne();
        Mockito.when(gameMapper.mapSearchResponse(searchResponseFixture)).thenReturn(gameSearchFixture);

        // when
        GameSearch gameSearch = searchGamesUseCase.execute(query);

        // then
        Assertions.assertEquals(1, gameSearch.getGames().size());
        Assertions.assertEquals("20-01-1996 08:30:00", gameSearch.getRequestDateTime());

        Game game = gameSearch.getGames().get(0);
        Assertions.assertEquals("Starfox", game.getName());
        Assertions.assertEquals("Um jogo bacana", game.getDescription());
        Assertions.assertEquals("url da imagem", game.getCoverImage());
        Assertions.assertEquals("1997-04-27", game.getOriginalReleaseDate());

        Mockito.verify(searchGamesGateway, Mockito.times(1)).searchGames(query);
        Mockito.verify(gameMapper, Mockito.times(1)).mapSearchResponse(searchResponseFixture);
    }
}