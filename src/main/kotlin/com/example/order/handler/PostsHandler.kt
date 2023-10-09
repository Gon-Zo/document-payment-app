package com.example.order.handler

import com.example.order.entity.CreatePostCommand
import com.example.order.entity.Post
import com.example.order.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

private val logger = LoggerFactory.getLogger(PostsHandler::class.java)

@Component
class PostsHandler(
    private val postRepository: PostRepository
) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(CreatePostCommand::class.java)
            .flatMap {
                Post.of(it.title, it.content).run {
                    postRepository.save(this)
                        .convert()
                        .toMono()
                }
            }
            .flatMap { _ -> ServerResponse.status(HttpStatus.OK).build() }
    }
}
