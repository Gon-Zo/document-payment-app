package com.example.document.web.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWebController {

    @GetMapping("hello")
    fun getMap(): Map<String, String> = mapOf(
        "health" to "OK"
    )

    @GetMapping("/hello/{name}")
    fun getName(@PathVariable name: String): String = name
}
