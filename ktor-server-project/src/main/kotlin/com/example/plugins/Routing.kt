package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.tasks.NewTask
import com.example.tasks.TaskService
import com.example.users.NewUser
import com.example.users.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Application.configureRouting() {

    val secret = "secret"
    val issuer = "http://0.0.0.0:8080"
    val audience = "users"

    routing {
        val taskService by inject<TaskService>()
        val userService by inject<UserService>()

        post("/register") {
            val newUser = call.receive<NewUser>()
            if (userService.findByUsername(newUser.username) != null) {
                call.respond(HttpStatusCode.Conflict, "Username already exists")
                return@post
            }
            val createdUser = userService.create(newUser)
            call.respond(HttpStatusCode.Created, "User registered successfully")
        }

        post("/login") {
            val user = call.receive<NewUser>()
            val foundUser = userService.findByUsername(user.username)

            if (foundUser == null || !BCrypt.checkpw(user.password, foundUser.password)) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                return@post
            }

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", foundUser.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 600000)) // 10분 유효
                .sign(Algorithm.HMAC256(secret))

            call.respond(hashMapOf("token" to token))
        }

        authenticate("auth-jwt") {
            route("/tasks") {
                get {
                    call.respond(taskService.getAll())
                }

                get("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                        return@get
                    }
                    val task = taskService.getById(id)
                    if (task != null) {
                        call.respond(task)
                    } else {
                        call.respondText("Task not found", status = HttpStatusCode.NotFound)
                    }
                }

                post {
                    val newTask = call.receive<NewTask>()
                    val createdTask = taskService.create(newTask)
                    call.respond(createdTask)
                }

                put("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                        return@put
                    }
                    val task = call.receive<NewTask>()
                    val updatedTask = taskService.update(id, task)
                    if (updatedTask != null) {
                        call.respond(updatedTask)
                    } else {
                        call.respondText("Task not found", status = HttpStatusCode.NotFound)
                    }
                }

                delete("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if (id == null) {
                        call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
                        return@delete
                    }
                    if (taskService.delete(id)) {
                        call.respondText("Task deleted", status = HttpStatusCode.OK)
                    } else {
                        call.respondText("Task not found", status = HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}