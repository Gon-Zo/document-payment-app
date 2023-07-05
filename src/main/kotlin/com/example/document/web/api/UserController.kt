package com.example.document.web.api

import com.example.document.infra.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userRepository: UserRepository
) {

    @GetMapping("users/{id}")
    suspend fun getOne(@PathVariable id: Long) = userRepository.findById(id)
}
