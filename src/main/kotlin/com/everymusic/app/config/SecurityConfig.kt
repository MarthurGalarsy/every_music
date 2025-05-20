package com.everymusic.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it.anyRequest().permitAll() } // すべて許可
            .csrf { it.disable() } // CSRFも一旦無効化（必要に応じて有効化）
            .formLogin { it.disable() } // デフォルトログイン画面の無効化
        return http.build()
    }
}