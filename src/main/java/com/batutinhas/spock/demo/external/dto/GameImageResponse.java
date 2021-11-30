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
public class GameImageResponse {

    @JsonProperty("original_url")
    private String originalUrl;
}
