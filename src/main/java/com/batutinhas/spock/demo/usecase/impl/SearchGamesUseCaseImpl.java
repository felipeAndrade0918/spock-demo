package com.batutinhas.spock.demo.usecase.impl;

import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.external.gateway.SearchGamesGateway;
import com.batutinhas.spock.demo.mapper.GameMapper;
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchGamesUseCaseImpl implements SearchGamesUseCase {

    private final SearchGamesGateway searchGamesGateway;

    private final GameMapper gameMapper;

    @Override
    public GameSearch execute(String query) {
        SearchResponse searchResponse = searchGamesGateway.searchGames(query);
        return gameMapper.mapSearchResponse(searchResponse);
    }
}
