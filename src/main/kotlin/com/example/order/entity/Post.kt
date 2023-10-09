package com.example.order.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "POSTS")
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
    var title: String?,
    var content: String?,
) {

    constructor() : this(null, null, null)

    companion object {
        fun of(title: String?, content: String?): Post {
            return Post(
                id = null,
                title = title,
                content = content,
            )
        }
    }
}


data class CreatePostCommand(
    var title: String?, var content: String?
)
