package com.example.users

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}

data class User(val id: Int, val username: String, val password: String)

data class NewUser(val username: String, val password: String)