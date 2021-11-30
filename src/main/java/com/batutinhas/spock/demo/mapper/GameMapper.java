package com.batutinhas.spock.demo.mapper;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.external.dto.SearchResponse;

import java.util.List;

public interface GameMapper {

    List<Game> mapSearchResponse(SearchResponse searchResponse);
}
