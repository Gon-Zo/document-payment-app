package com.example.order

import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class BeanConfig {

    @Bean
    fun sessionFactory(): Mutiny.SessionFactory =
        Persistence.createEntityManagerFactory("blogPU")
            .unwrap(Mutiny.SessionFactory::class.java)


    @Bean
    fun handler(posts: PostsHandler) = router {
        path("/posts").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST { posts.create(it) }
                GET { posts.getOne(it) }
            }
        }
    }
}
