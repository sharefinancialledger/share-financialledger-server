package com.sharefinancialledger.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
    }
}