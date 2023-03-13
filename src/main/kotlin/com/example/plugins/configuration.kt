package com.example.plugins

import com.example.hello.DAOFacade
import com.example.hello.DAOFacadeImpl
import com.example.hello.HelloService
import com.example.hello.HelloServiceImpl
import com.example.hello.customerRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.routing.routing
import org.koin.dsl.module

val appModule = module {
    single {
        HelloServiceImpl(get()) as HelloService
    }

    single {
        DAOFacadeImpl() as DAOFacade
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
