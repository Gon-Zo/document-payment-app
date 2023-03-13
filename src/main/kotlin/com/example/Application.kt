package com.example

import com.example.plugins.appModule
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application
import org.koin.core.context.startKoin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {

    startKoin {
        slf4jLogger()
        modules(appModule)
    }

    configureRouting()
    configureSerialization()
}
