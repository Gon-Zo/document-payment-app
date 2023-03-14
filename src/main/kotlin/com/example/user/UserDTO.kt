package com.example.user

import kotlinx.serialization.Serializable


/**
 * Data Access Layer 와 Service 혹은 다른 레이어 간의 User 통신 객체
 */
data class UserInputModel(val email: String, val password: String, val type: UserRole = UserRole.USER)

data class UserOutputModel(val id: Long, val email: String, val password: String, val type: UserRole)

/**
 * User Routing 과 User Service 를 중간에 DTO 객체를 둠으로 인해서 결합도를 낮추기 위한 객체
 * 해당하는 method 를 통해서 항상 레이어 간의 통신이 필요
 */
data class UserCreatedModel(
    val email: String,
    val password: String
) {

    fun toEntity(): UserInputModel = UserInputModel(email, password)
}

/**
 * 유저 생성 API 의 Request 이다.
 */
@Serializable
data class UserSaveRequest(val email: String, val password: String) {
    fun toModel() = UserCreatedModel(email, password)
}

@Serializable
data class UserSaveResponse(val email: String?, val password: String?, val type: UserRole?) {
    constructor(user: UserOutputModel?) : this(user?.email, user?.password, user?.type)
}
