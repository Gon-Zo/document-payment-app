package com.example.hello

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

@Serializable
data class Customer(val name: String, val content: String)

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
            val customer = call.receive<Customer>()
            call.respond(status = HttpStatusCode.Created, message = customer)
        }
        patch {

        }
        put {

        }
        delete("{id?}") {

        }
    }
}
