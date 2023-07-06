package com.example.document.infra.handler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterConfiguration {

    @Bean
    fun route(playerHandler: PlayerHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route(GET("/player/{name}"), playerHandler::getName)
            .filter(ExampleHandlerFilterFunction())
    }
}
