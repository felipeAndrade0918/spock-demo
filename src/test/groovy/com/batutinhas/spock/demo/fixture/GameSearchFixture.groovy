package com.batutinhas.spock.demo.fixture


import com.batutinhas.spock.demo.domain.GameSearch

class GameSearchFixture {

    static GameSearch getOne() {
        return GameSearch.builder()
                .games([GameFixture.getOneValid()])
                .requestDateTime("")
                .build()
    }
}
