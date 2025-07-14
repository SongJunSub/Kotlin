package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<User> {
            if (it.name.isBlank()) {
                ValidationResult.Invalid("Name cannot be blank")
            } else if (it.age <= 0) {
                ValidationResult.Invalid("Age must be a positive number")
            } else {
                ValidationResult.Valid
            }
        }
    }
}
