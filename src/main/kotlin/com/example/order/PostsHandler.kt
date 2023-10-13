package com.example.order


import io.smallrye.mutiny.converters.uni.UniReactorConverters
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class PostsHandler(
    private val postRepository: PostRepository
) {

    fun getOne(req: ServerRequest): Mono<ServerResponse> =
        postRepository.findOne()
            .convert()
            .with(UniReactorConverters.toMono())
            .flatMap { p ->
                ServerResponse.ok()
                    .body(Mono.just(p!!), Post::class.java)
            }

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(CreatePostCommand::class.java)
            .flatMap {
                Post.of(it.title, it.content).run {
                    postRepository.save(this)
                        .convert()
                        .with(UniReactorConverters.toMono())

                }
            }
            .flatMap { _ -> ServerResponse.status(HttpStatus.OK).build() }
    }

    fun delete(req: ServerRequest) =
        postRepository.delete()
            .convert()
            .with(UniReactorConverters.toMono())
            .flatMap { p -> ServerResponse.ok().build() }
}
