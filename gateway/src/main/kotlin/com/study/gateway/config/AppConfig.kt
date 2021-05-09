package com.study.gateway.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(JsonFeature)
        expectSuccess = false
    }

}