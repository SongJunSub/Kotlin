package com.example.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcURL = System.getenv("DB_URL") ?: "jdbc:mysql://localhost:3306/your_database_name"
        val user = System.getenv("DB_USER") ?: "your_username"
        val password = System.getenv("DB_PASSWORD") ?: "your_password"
        val database = Database.connect(createHikariDataSource(jdbcURL, driverClassName, user, password))
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    private fun createHikariDataSource(url: String, driver: String, user: String, pass: String) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        username = user
        password = pass
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
