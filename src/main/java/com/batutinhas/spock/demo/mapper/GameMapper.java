package com.batutinhas.spock.demo.mapper;

import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.external.dto.SearchResponse;

public interface GameMapper {

    GameSearch mapSearchResponse(SearchResponse searchResponse);
}
