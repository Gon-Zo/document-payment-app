package com.example.user


interface UserService {
    suspend fun created(model: UserCreatedModel): UserOutputModel?
}

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun created(model: UserCreatedModel): UserOutputModel? = userRepository.save(model.toEntity())

}
