package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.service.UserService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.configureRouting() {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {
        val userService by inject<UserService>()

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
