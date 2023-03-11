package com.example.hello

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.customerRouting() {
    route("/customer") {
        get {

            val map = mapOf(
                "test" to "test"
            )

            call.respond(map)
        }
        get("{id?}") {
        }
        post {

        }
        patch {

        }
        put {

        }
        delete("{id?}") {

        }
    }
}
