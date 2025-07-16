package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.tasks.TaskService
import com.example.users.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureKoin()
    configureSecurity()
    configureSerialization()
    configureRouting()
}

fun Application.configureKoin() {
    install(Koin) {
        modules(appModule)
    }
}

val appModule = module {
    single { TaskService() }
    single { UserService() }
}

fun Application.configureSecurity() {
    val secret = "secret" // 실제 환경에서는 환경 변수나 설정 파일에서 관리해야 합니다.
    val issuer = "http://0.0.0.0:8080"
    val audience = "users"
    val myRealm = "Access to 'tasks'"

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