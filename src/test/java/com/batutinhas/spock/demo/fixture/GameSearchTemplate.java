package com.batutinhas.spock.demo.fixture;

import com.batutinhas.spock.demo.domain.GameSearch;

import java.util.List;

public class GameSearchTemplate {

    public static GameSearch getOne() {
        return GameSearch.builder()
                .games(List.of(GameTemplate.getOneValid()))
                .requestDateTime("20-01-1996 08:30:00")
                .build();
    }
}
