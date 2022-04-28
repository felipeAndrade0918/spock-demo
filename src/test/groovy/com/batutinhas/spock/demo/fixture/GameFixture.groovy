package com.batutinhas.spock.demo.fixture

import com.batutinhas.spock.demo.domain.Game

class GameFixture {

    static Game getOneValid() {
        return Game.builder()
                .name("Starfox")
                .description("Um jogo bacana")
                .coverImage("url da imagem")
                .originalReleaseDate("1997-04-27")
                .build()
    }
}
