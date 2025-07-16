package com.example

import com.example.tasks.NewTask
import com.example.users.NewUser
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class AuthApiTest {

    @Test
    fun testRegisterUser() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        val user = NewUser("testuser", "password")
        val response = client.post("/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("User registered successfully", response.bodyAsText())
    }

    @Test
    fun testLoginUser() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Register a user first
        client.post("/register") {
            contentType(ContentType.Application.Json)
            setBody(NewUser("loginuser", "password"))
        }

        val user = NewUser("loginuser", "password")
        val response = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val token = response.body<Map<String, String>>()["token"]
        assertNotNull(token)
    }

    @Test
    fun testAccessProtectedTasks() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Register and login to get a token
        client.post("/register") {
            contentType(ContentType.Application.Json)
            setBody(NewUser("protecteduser", "password"))
        }
        val loginResponse = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(NewUser("protecteduser", "password"))
        }
        val token = loginResponse.body<Map<String, String>>()["token"]

        // Access protected /tasks endpoint
        val response = client.get("/tasks") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testAccessProtectedTasksWithoutToken() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = client.get("/tasks")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun testAccessProtectedTasksWithInvalidToken() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        val response = client.get("/tasks") {
            header(HttpHeaders.Authorization, "Bearer invalid_token")
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }
}