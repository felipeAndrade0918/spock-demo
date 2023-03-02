package com.batutinhas.spock.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Game implements Comparable<Game> {

    private String name;
    private String description;
    private String coverImage;
    private String originalReleaseDate;

    @Override
    public int compareTo(Game otherGame) {
        return this.getName().compareTo(otherGame.getName());
    }
}
