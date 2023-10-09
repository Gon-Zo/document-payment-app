package com.example.order

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable


@Entity
@Table(name = "POSTS")
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?,
    var title: String?,
    var content: String?,
) : Serializable {

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
