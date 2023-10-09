package com.example.order.repository;

import com.example.order.entity.Post;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;


//@Repository
public class PostJavaRepository {

    private final Mutiny.SessionFactory sessionFactory;

    public PostJavaRepository(Mutiny.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Uni<Post> save(Post post) {
        return sessionFactory.openSession()
                .chain(session -> session.persist(post))
                .eventually(sessionFactory::close)
                .replaceWith(post);
    }
}
