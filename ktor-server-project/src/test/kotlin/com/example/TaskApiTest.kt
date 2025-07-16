package com.example

import com.example.tasks.NewTask
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.*

class TaskApiTest {

    @Test
    fun testCreateTask() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        val task = NewTask("Test Task", "This is a test description")
        val response = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val createdTask = response.body<Map<String, Any>>()
        assertEquals("Test Task", createdTask["title"])
        assertEquals("This is a test description", createdTask["description"])
    }

    @Test
    fun testGetAllTasks() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Create a task first
        client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(NewTask("Task 1", "Description 1"))
        }
        val response = client.get("/tasks")
        assertEquals(HttpStatusCode.OK, response.status)
        val tasks = response.body<List<Map<String, Any>>>()
        assertTrue(tasks.isNotEmpty())
        assertEquals("Task 1", tasks[0]["title"])
    }

    @Test
    fun testGetTaskById() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Create a task first
        val createdResponse = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(NewTask("Task to find", "Description to find"))
        }
        val createdTask = createdResponse.body<Map<String, Any>>()
        val taskId = createdTask["id"] as Int

        val response = client.get("/tasks/$taskId")
        assertEquals(HttpStatusCode.OK, response.status)
        val foundTask = response.body<Map<String, Any>>()
        assertEquals("Task to find", foundTask["title"])
    }

    @Test
    fun testUpdateTask() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Create a task first
        val createdResponse = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(NewTask("Task to update", "Old description"))
        }
        val createdTask = createdResponse.body<Map<String, Any>>()
        val taskId = createdTask["id"] as Int

        val updatedTask = NewTask("Updated Task", "New description")
        val response = client.put("/tasks/$taskId") {
            contentType(ContentType.Application.Json)
            setBody(updatedTask)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val resultTask = response.body<Map<String, Any>>()
        assertEquals("Updated Task", resultTask["title"])
        assertEquals("New description", resultTask["description"])
    }

    @Test
    fun testDeleteTask() = testApplication {
        val client = createClient { install(ContentNegotiation) { json() } }
        // Create a task first
        val createdResponse = client.post("/tasks") {
            contentType(ContentType.Application.Json)
            setBody(NewTask("Task to delete", "Description to delete"))
        }
        val createdTask = createdResponse.body<Map<String, Any>>()
        val taskId = createdTask["id"] as Int

        val response = client.delete("/tasks/$taskId")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Task deleted", response.bodyAsText())

        val getResponse = client.get("/tasks/$taskId")
        assertEquals(HttpStatusCode.NotFound, getResponse.status)
    }
}