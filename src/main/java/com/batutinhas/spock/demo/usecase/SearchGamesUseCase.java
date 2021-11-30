package com.batutinhas.spock.demo.usecase;

import com.batutinhas.spock.demo.domain.Game;

import java.util.List;

public interface SearchGamesUseCase {

    List<Game> execute(String query);
}
