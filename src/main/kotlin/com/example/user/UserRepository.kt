package com.example.user

import com.example.common.ExposedRepository
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

interface UserRepository : ExposedRepository<UserOutputModel, UserInputModel, Long> {
}

class UserRepositoryImpl : UserRepository {

    private fun resultRowToUser(row: ResultRow) = UserOutputModel(
        id = row[User.id].value,
        email = row[User.email],
        password = row[User.password],
        type = row[User.type]
    )

    override suspend fun save(t: UserInputModel): UserOutputModel? = dbQuery {
        val insertData = User.insert {
            it[User.email] = t.email
            it[User.password] = t.password
            it[User.type] = t.type
        }
        insertData.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun findAll(): Collection<UserOutputModel> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Long): UserOutputModel? {
        TODO("Not yet implemented")
    }

    override suspend fun deleted(m: UserInputModel): Int {
        TODO("Not yet implemented")
    }


}


