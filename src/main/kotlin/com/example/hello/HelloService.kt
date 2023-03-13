package com.example.hello

interface HelloService {
    fun getMessage(): String
}

class HelloServiceImpl : HelloService {
    override fun getMessage(): String = "String"
}
