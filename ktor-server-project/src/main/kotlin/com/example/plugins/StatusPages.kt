package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> {
            call, cause ->
                when(cause) {
                    is RequestValidationException -> call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.reasons.joinToString()))
                    else -> call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Something went wrong"))
                }
        }
    }
}
