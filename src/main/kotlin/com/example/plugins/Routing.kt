package com.example.plugins

import com.example.hello.customerRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
