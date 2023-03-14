package com.example.user

import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * User Entity 영역
 * 실제 exposed 의 엔티티 그리고 User 관련 enum 이 포함 된다.
 */

object User : LongIdTable("users") {
    var email = varchar("email", 255)
    var password = varchar("password", 255)
    var type = enumeration("type", UserRole::class)
}

enum class UserRole {
    USER,
    ADMIN
}
