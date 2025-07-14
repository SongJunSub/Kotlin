package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.swagger.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.configureRouting() {

    val secret = System.getenv("JWT_SECRET") ?: "secret"
    val issuer = System.getenv("JWT_ISSUER") ?: "http://0.0.0.0:8080"
    val audience = System.getenv("JWT_AUDIENCE") ?: "users"

    routing {
        val userService by inject<UserService>()

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        get("/") {
            call.respondText("Hello World!")
        }

        post("/login") {
            val user = call.receive<User>()
            // In a real application, you would check the user's credentials against a database.
            // For this example, we'll just generate a token for any user.
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.name)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(mapOf("token" to token))
        }

        authenticate("auth-jwt") {
            route("/users") {
                get {
                    call.respond(userService.findAll())
                }
                post {
                    val user = call.receive<User>()
                    call.respond(userService.create(user))
                }
            }
        }
    }
}
