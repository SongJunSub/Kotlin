package functionalprogramming

import arrow.core.Option
import arrow.core.none
import arrow.core.some

fun findUserById(id: Int): Option<String> {
    return when (id) {
        1 -> "Alice".some()
        2 -> "Bob".some()
        else -> none()
    }
}

fun main() {
    val user1 = findUserById(1)
    when (user1) {
        is Option.Some -> println("Found user: \${user1.value}")
        is Option.None -> println("User not found")
    }

    val user3 = findUserById(3)
    when (user3) {
        is Option.Some -> println("Found user: \${user3.value}")
        is Option.None -> println("User not found")
    }

    // Using map and fold
    val userName = findUserById(2).map { it.uppercase() }.fold({ "Guest" }, { it })
    println("User name (mapped): \$userName")

    val noUserName = findUserById(4).map { it.uppercase() }.fold({ "Guest" }, { it })
    println("User name (not found, folded): \$noUserName")
}