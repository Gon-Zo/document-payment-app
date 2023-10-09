package com.example.order

import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny

fun main() {

    val sessionFactory =
        Persistence.createEntityManagerFactory("blogPU")
            .unwrap(Mutiny.SessionFactory::class.java)

    runCatching {

        sessionFactory.withSession { s -> s.find(Post::class.java, 1L) }
            .invoke { p ->
                println("id : ${p.id} , title : ${p.title}, content : ${p.content}")
            }.await()
            .indefinitely()


//        val post = Post.of(
//            title = "test1",
//            content = "test...."
//        )
//
//        sessionFactory.withSession { session ->
//            session.persist(post)
//        }
//            .await().indefinitely()


    }.onSuccess {
        sessionFactory.close()
    }.onFailure {
        throw it
    }
}
