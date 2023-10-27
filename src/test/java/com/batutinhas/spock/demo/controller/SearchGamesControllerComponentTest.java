package com.batutinhas.spock.demo.controller;

import com.batutinhas.spock.demo.Application;
import com.batutinhas.spock.demo.config.ClockMockConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = {Application.class, ClockMockConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "local")
class SearchGamesControllerComponentTest {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private final WireMockServer wireMock = new WireMockServer(WireMockSpring.options()
            .port(8888)
            .usingFilesUnderDirectory("config/wiremock/mocks"));

    @BeforeEach
    public void beforeEach() {
        wireMock.start();
    }

    @AfterEach
    public void afterEach() {
        wireMock.stop();
    }

    @Test
    @DisplayName("O endpoint principal deve retornar uma lista de jogos baseado no nome de um jogo")
    void shouldListGamesBasedOnTheQuery() throws Exception {
        // given
        String query = "Star Fox 64";

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
        Assertions.assertEquals("Star Fox 64", gameNode.get("name").asText());
        Assertions.assertEquals("Return to the cockpit of the Arwing fighter craft and save the Lylat system from conquest in this re-telling of the original Star Fox.",
                gameNode.get("description").asText());
        Assertions.assertEquals("https://www.giantbomb.com/a/uploads/original/8/82063/2836282-sf64.jpg",
                gameNode.get("coverImage").asText());
        Assertions.assertEquals("1997-04-27", gameNode.get("originalReleaseDate").asText());
    }
}