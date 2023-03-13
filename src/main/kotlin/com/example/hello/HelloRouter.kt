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
import org.koin.ktor.ext.inject

@Serializable
data class Customer(val name: String, val content: String)

@Serializable
data class ArticleRequest(val title: String, val body: String) {

    fun toModel(): ArticleModel = ArticleModel(title, body)
}

fun Route.customerRouting() {

    val service by inject<HelloService>()

    route("/customer") {
        get {
            call.respond(status = HttpStatusCode.OK, message = service.getList())
        }
        get("/{id}") {
            val id = call.parameters["id"]
            call.respond(id.toString())
        }
        post {

            val body = call.receive<ArticleRequest>()

            val entity = service.created(body.toModel()) ?: Article()

            call.respond(status = HttpStatusCode.Created, message = entity)
        }
        patch {

        }
        put {

        }
        delete("{id?}") {

        }
    }
}
