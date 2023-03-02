package com.batutinhas.spock.demo.mapper.impl;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.mapper.GameMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameMapperImpl implements GameMapper {

    @Override
    public List<Game> mapSearchResponse(SearchResponse searchResponse) {
        return searchResponse.getResults().stream()
                .map(result ->
                        Game.builder()
                                .name(result.getName())
                                .description(result.getDeck())
                                .coverImage(result.getImage().getOriginalUrl())
                                .originalReleaseDate(result.getOriginalReleaseDate())
                                .build())
                .sorted()
                .collect(Collectors.toList());
    }
}
