package com.example.document.infra.filter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class ExampleWebFilter : WebFilter {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        logger.info("Working Use to Trace Logger")

        // Http Header Add Key Web Filter and Value is Web Filter Test
        exchange.response
            .headers
            .add("web-filter", "web-filter-test")

        return chain.filter(exchange)
    }
}
