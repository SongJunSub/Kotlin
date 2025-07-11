package com.example.repository

import com.example.plugins.DatabaseFactory.dbQuery
import com.example.plugins.User
import com.example.plugins.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserRepositoryImpl : UserRepository {
    override suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map(::toUser)
    }

    override suspend fun create(user: User): User = dbQuery {
        val insertStatement = Users.insert {
            it[name] = user.name
            it[age] = user.age
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toUser)!!
    }

    private fun toUser(row: ResultRow): User = User(
        id = row[Users.id],
        name = row[Users.name],
        age = row[Users.age]
    )
}
