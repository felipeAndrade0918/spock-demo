package com.batutinhas.spock.demo.usecase;

import com.batutinhas.spock.demo.domain.GameSearch;

public interface SearchGamesUseCase {

    GameSearch execute(String query);
}
