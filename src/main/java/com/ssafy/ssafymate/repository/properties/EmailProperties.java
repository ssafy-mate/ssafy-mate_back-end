package com.ssafy.ssafymate.repository.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
@Configuration
public class EmailProperties {
    private String name;
    private String link;
    private Long validTime;
}