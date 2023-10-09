package com.example.order.repository

import com.example.order.entity.Post
import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository


private val logger = LoggerFactory.getLogger(PostRepository::class.java)

@Repository
class PostRepository(
    private val sessionFactory: Mutiny.SessionFactory
) {

    fun save(post: Post): Uni<Post> {

        return sessionFactory.withSession { session: Mutiny.Session ->
            session.persist(post)
        }
            .replaceWith(post)
    }

}
