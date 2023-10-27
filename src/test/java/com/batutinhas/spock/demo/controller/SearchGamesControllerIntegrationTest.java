package com.batutinhas.spock.demo.controller;

import com.batutinhas.spock.demo.Application;
import com.batutinhas.spock.demo.config.ClockMockConfig;
import com.batutinhas.spock.demo.external.client.GiantBombClient;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.fixture.SearchResponseTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = {Application.class, ClockMockConfig.class})
@AutoConfigureMockMvc
class SearchGamesControllerIntegrationTest {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiantBombClient giantBombClient;

    @Test
    @DisplayName("O endpoint principal deve retornar uma lista de jogos baseado no nome de um jogo")
    void shouldListGamesBasedOnTheQuery() throws Exception {
        // given
        String query = "Starfox";

        // Uma resposta válida da API
        SearchResponse searchResponse = SearchResponseTemplate.getOneValid();
        Mockito.when(giantBombClient.searchGames(query)).thenReturn(searchResponse);

        // when
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/search")
                                .queryParam("query", query))
                .andReturn();

        // then
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        JsonNode jsonNode = OBJECT_MAPPER.readTree(contentAsString);

        Assertions.assertEquals("20-01-1996 08:30:00", jsonNode.get("requestDateTime").asText());

        JsonNode gamesNode = jsonNode.get("games");
        Assertions.assertEquals(1, gamesNode.size());

        JsonNode gameNode = gamesNode.get(0);
        Assertions.assertEquals("Starfox", gameNode.get("name").asText());
        Assertions.assertEquals("Um jogo bacana", gameNode.get("description").asText());
        Assertions.assertEquals("url da imagem", gameNode.get("coverImage").asText());
        Assertions.assertEquals("1997-04-27", gameNode.get("originalReleaseDate").asText());
    }
}