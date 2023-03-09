package com.batutinhas.spock.demo.mapper.impl;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.domain.GameSearch;
import com.batutinhas.spock.demo.external.dto.SearchResponse;
import com.batutinhas.spock.demo.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GameMapperImpl implements GameMapper {

    private static final String REQUEST_DATE_TIME_PATTERN = "dd-MM-YYYY HH:mm:ss";

    private final Clock clock;

    @Override
    public GameSearch mapSearchResponse(SearchResponse searchResponse) {
        return GameSearch.builder()
                .games(searchResponse.getResults().stream()
                        .map(result ->
                                Game.builder()
                                        .name(result.getName())
                                        .description(result.getDeck())
                                        .coverImage(result.getImage().getOriginalUrl())
                                        .originalReleaseDate(result.getOriginalReleaseDate())
                                        .build())
                        .collect(Collectors.toList()))
                .requestDateTime(buildRequestDateTime())
                .build();
    }

    private String buildRequestDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(REQUEST_DATE_TIME_PATTERN);
        LocalDateTime now = LocalDateTime.now(clock);
        return now.format(dateTimeFormatter);
    }
}
