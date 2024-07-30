package com.example.digitalChiefTest.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search.filter")
@Getter
@Setter
@NoArgsConstructor
public class SearchConfig {
    private boolean enabled;
    private String color;
    private boolean availability;
}
