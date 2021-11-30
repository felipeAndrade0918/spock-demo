package com.batutinhas.spock.demo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GameResponse {

    private String deck;
    private String name;
    @JsonProperty("original_release_date")
    private String originalReleaseDate;
    private GameImageResponse image;
}
