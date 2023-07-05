package com.example.document.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "users")
data class User(
    @Id
    val id: Long,
    val email: String,
    val password : String
) {
}
