package com.pczigany.bv_homework.configuration

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OkHttpConfiguration {

    @Bean
    fun configureOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}
