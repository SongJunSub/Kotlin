package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    val secret = System.getenv("JWT_SECRET") ?: "secret"
    val issuer = System.getenv("JWT_ISSUER") ?: "http://0.0.0.0:8080"
    val audience = System.getenv("JWT_AUDIENCE") ?: "users"
    val myRealm = System.getenv("JWT_REALM") ?: "ktor sample app"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())
            validate {
                if (it.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
    }
}
