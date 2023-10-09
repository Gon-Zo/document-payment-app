package com.example.order.config

import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DataBaseConfig {


    @Bean
    fun sessionFactory(): Mutiny.SessionFactory =
        Persistence.createEntityManagerFactory("blogPU")
            .unwrap(Mutiny.SessionFactory::class.java)

}
