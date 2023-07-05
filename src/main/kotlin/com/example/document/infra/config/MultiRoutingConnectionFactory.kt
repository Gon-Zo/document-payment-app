package com.example.document.infra.config

import org.slf4j.LoggerFactory
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import reactor.core.publisher.Mono

class MultiRoutingConnectionFactory : AbstractRoutingConnectionFactory() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun determineCurrentLookupKey(): Mono<Any> = Mono.just(WriteDataSourceProperties.KEY)
}
