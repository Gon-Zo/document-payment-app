package com.example.order

import io.vertx.core.spi.resolver.ResolverProvider.factory
import jakarta.persistence.Persistence.createEntityManagerFactory
import org.hibernate.reactive.stage.Stage
import java.awt.print.Book


fun main() {

    val sf = createEntityManagerFactory("blogPU").unwrap(Stage.SessionFactory::class.java)
    runCatching {


        sf.withTransaction { s, t ->
            s.find(Post::class.java, 1L)
                .thenCompose { s.remove(it) }
                .toCompletableFuture().join()
        }

    }.also {
        sf.close()
    }.getOrNull()
}
