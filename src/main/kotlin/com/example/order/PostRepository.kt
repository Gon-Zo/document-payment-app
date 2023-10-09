package com.example.order

import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class PostRepository(
    private val sessionFactory: Mutiny.SessionFactory
) {

    fun findOne(id: Long = 1L): Uni<Post?> =
        sessionFactory.withSession { s -> s.find(Post::class.java, id) }

    fun save(post: Post): Uni<Post> {

        return sessionFactory.withSession { session: Mutiny.Session ->
            session.persist(post)
        }
            .replaceWith(post)
    }

}
