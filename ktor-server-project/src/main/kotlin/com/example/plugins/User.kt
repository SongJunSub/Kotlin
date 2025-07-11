package com.example.plugins

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(val id: Int, val name: String, val age: Int)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val age = integer("age")

    override val primaryKey = PrimaryKey(id)
}
