package com.gaaji.chat.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Getter
public class MyConfig {
    private final EventProps eventProps;
}
