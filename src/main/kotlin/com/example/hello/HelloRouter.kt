package com.example.hello

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable
import java.lang.RuntimeException

@Serializable
data class Customer(val name: String, val content: String)

fun Route.customerRouting() {


    route("/customer") {
        get {

            val name = call.request.queryParameters["name"] ?: "test.."

            val map = mapOf(
                "name" to name
            )

            call.respond(status = HttpStatusCode.OK, message = map)
        }
        get("/{id}") {
            val id = call.parameters["id"]
            call.respond(id.toString())
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
