package com.example.order.config

import com.example.order.handler.PostsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class WebConfig(
    private val handler: PostsHandler
) {

    @Bean
    fun handler() = router {
        path("/posts").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST { handler.create(it) }
            }
        }

    }
}
