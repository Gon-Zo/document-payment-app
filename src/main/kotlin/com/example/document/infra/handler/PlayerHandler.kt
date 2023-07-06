package com.example.document.infra.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class PlayerHandler {

    fun getName(request: ServerRequest): Mono<ServerResponse> =
        ok()
            .body(
                Mono.just(request.pathVariable("name")),
                String::class.java
            )
}
