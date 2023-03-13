package com.example.plugins

import com.example.hello.HelloService
import com.example.hello.HelloServiceImpl
import com.example.hello.customerRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.routing.routing
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::HelloServiceImpl) {
        bind<HelloService>()
    }
}

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    install(RequestValidation)
}
