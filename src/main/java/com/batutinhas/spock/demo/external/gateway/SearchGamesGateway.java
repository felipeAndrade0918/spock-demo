package com.batutinhas.spock.demo.external.gateway;

import com.batutinhas.spock.demo.external.dto.SearchResponse;

public interface SearchGamesGateway {

    SearchResponse searchGames(String query);
}
