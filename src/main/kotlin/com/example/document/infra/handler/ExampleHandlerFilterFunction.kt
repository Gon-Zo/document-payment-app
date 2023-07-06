package com.example.document.infra.handler

import io.netty.handler.codec.http.HttpResponseStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ExampleHandlerFilterFunction : HandlerFilterFunction<ServerResponse, ServerResponse> {

    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {
        if (request.pathVariable("name") == "test") {
            return ServerResponse.status(HttpResponseStatus.FORBIDDEN.code()).build();
        }
        return next.handle(request);
    }
}
