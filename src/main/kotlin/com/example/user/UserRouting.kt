package com.example.user

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.userRouting() {

    val service by inject<UserService>()

    route("/user") {

        post {

            val body = call.receive<UserSaveRequest>()

            val data = UserSaveResponse(service.created(body.toModel()))

            call.respond(
                status = HttpStatusCode.Created,
                message = data
            )
        }

    }

}
