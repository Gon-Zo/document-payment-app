package com.example.order

import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny

fun main() {

//    val emf = Persistence.createEntityManagerFactory("blogPU")
//
//    val sessionFactory = emf.unwrap(Stage.SessionFactory::class.java)

    val sessionFactory =
        Persistence.createEntityManagerFactory("blogPU")
            .unwrap(Mutiny.SessionFactory::class.java)

    runCatching {

//        Post.of(
//            title = "test1",
//            content = "test...."
//        )


        delete(sessionFactory)

    }.also {
        sessionFactory.close()
    }.getOrNull()
}

private fun delete(sessionFactory: Mutiny.SessionFactory) {
    sessionFactory.withSession { session ->
        session.find(Post::class.java, 1L)
            .call { i -> session.remove(i) }
//            .call { _ -> session.flush() }
    }.await().indefinitely()
}

private fun findBy(sessionFactory: Mutiny.SessionFactory) {
    sessionFactory.withSession { s -> s.find(Post::class.java, 1L) }
        .invoke { p ->
            println("id : ${p.id} , title : ${p.title}, content : ${p.content}")
        }.await()
        .indefinitely()
}
