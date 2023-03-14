package com.example.common

interface ExposedRepository<T, M, ID> {
    suspend fun save(m: M): T?
    suspend fun findAll(): Collection<T>
    suspend fun findById(id: ID): T?
    suspend fun deleted(m: M): Int
}
