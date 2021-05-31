package com.study.favoritesservice.config

import com.study.favoritesservice.AuthInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Bean
    fun provideRestTemplate() : RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun provideAuthInterceptor(restTemplate: RestTemplate, environment: Environment) : AuthInterceptor {
        return AuthInterceptor(restTemplate, environment)
    }
}