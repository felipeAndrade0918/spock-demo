package com.batutinhas.spock.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
public class ClockMockConfig {

    @Primary
    @Bean
    Clock fixedClock() {
        return Clock.fixed(Instant.parse("1996-01-20T08:30:00.00Z"), ZoneOffset.UTC);
    }
}
