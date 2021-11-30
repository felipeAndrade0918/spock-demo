package com.batutinhas.spock.demo.controller;

import com.batutinhas.spock.demo.domain.Game;
import com.batutinhas.spock.demo.usecase.SearchGamesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchGamesController {

    private final SearchGamesUseCase searchGamesUseCase;

    @GetMapping("/search")
    public List<Game> searchGames(@RequestParam String query) {
        return searchGamesUseCase.execute(query);
    }
}
