package com.example.user

import org.koin.dsl.module

val userModule = module {
    single {
        UserRepositoryImpl() as UserRepository
    }
    single {
        UserServiceImpl(get()) as UserService
    }
}
