package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import java.util.*

fun Application.configureRouting() {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {
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
                    val users = DatabaseFactory.dbQuery {
                        Users.selectAll().map { toUser(it) }
                    }
                    call.respond(users)
                }
                post {
                    val user = call.receive<User>()
                    val id = DatabaseFactory.dbQuery {
                        Users.insert {
                            it[name] = user.name
                            it[age] = user.age
                        }[Users.id]
                    }
                    call.respond(User(id, user.name, user.age))
                }
            }
        }
    }
}

private fun toUser(row: ResultRow): User = User(
    id = row[Users.id],
    name = row[Users.name],
    age = row[Users.age]
)
