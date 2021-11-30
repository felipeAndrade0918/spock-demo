package com.batutinhas.spock.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

    private String name;
    private String description;
    private String coverImage;
    private String originalReleaseDate;
}
