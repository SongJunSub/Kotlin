package com.example.users

import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserService {

    fun create(user: NewUser): User {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        val id = transaction {
            Users.insertAndGetId {
                it[username] = user.username
                it[password] = hashedPassword
            }
        }
        return findByUsername(user.username)!!
    }

    fun findByUsername(username: String): User? = transaction {
        Users.select { Users.username eq username }.map { User(it[Users.id], it[Users.username], it[Users.password]) }.singleOrNull()
    }
}