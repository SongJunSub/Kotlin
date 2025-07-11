package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        // User routes
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

private fun toUser(row: ResultRow): User = User(
    id = row[Users.id],
    name = row[Users.name],
    age = row[Users.age]
)
