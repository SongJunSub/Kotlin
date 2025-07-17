package com.example

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.example.tasks.Tasks
import com.example.users.Users

object DatabaseFactory {
    fun init() {
        val jdbcURL = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/your_database_name"
        val user = System.getenv("DB_USER") ?: "your_username"
        val password = System.getenv("DB_PASSWORD") ?: "your_password"
        val driverClassName = "org.postgresql.Driver"

        val database = Database.connect(jdbcURL, driverClassName, user, password)
        transaction(database) {
            SchemaUtils.create(Tasks, Users)
        }
    }
}