package com.batutinhas.spock.demo.external.gateway.impl;

import com.batutinhas.spock.demo.external.client.GiantBombClient;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchGamesGatewayImpl implements SearchGamesGateway {

    private final GiantBombClient giantBombClient;

    @Override
    public SearchResponse searchGames(String query) {
        return giantBombClient.searchGames(query);
    }
}
