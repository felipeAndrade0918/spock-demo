package com.batutinhas.spock.demo.fixture


import com.batutinhas.spock.demo.domain.GameSearch

class GameSearchFixture {

    static GameSearch getOne() {
        return GameSearch.builder()
                .games([GameFixture.getOneValid()])
                .requestDateTime("20-01-1996 08:30:00")
                .build()
    }
}
