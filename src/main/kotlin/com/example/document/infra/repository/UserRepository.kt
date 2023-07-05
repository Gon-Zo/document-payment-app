package com.example.document.infra.repository

import com.example.document.domain.User
import com.example.document.infra.config.R2dbcRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

@R2dbcRepository
interface UserRepository : CoroutineSortingRepository<User, Long> {

    suspend fun findById(id: Long): User?
}
