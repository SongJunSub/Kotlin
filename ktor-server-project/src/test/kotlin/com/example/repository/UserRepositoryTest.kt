package com.example.repository

import com.example.plugins.DatabaseFactory
import com.example.plugins.User
import com.example.plugins.Users
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.testcontainers.containers.MySQLContainer
import kotlin.test.assertEquals

class UserRepositoryTest {

    private lateinit var database: Database
    private val userRepository = UserRepositoryImpl()
    private val mysql = MySQLContainer("mysql:8.0.33")

    @Before
    fun setup() {
        mysql.start()
        database = Database.connect(
            url = mysql.jdbcUrl,
            driver = mysql.driverClassName,
            user = mysql.username,
            password = mysql.password
        )
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    @After
    fun tearDown() {
        mysql.stop()
    }

    @Test
    fun `create and findAll should work correctly`() = runBlocking {
        // Given
        val newUser = User(0, "Test User", 30)

        // When
        val createdUser = userRepository.create(newUser)
        val allUsers = userRepository.findAll()

        // Then
        assertEquals(1, allUsers.size)
        assertEquals("Test User", allUsers.first().name)
        assertEquals(createdUser, allUsers.first())
    }
}
