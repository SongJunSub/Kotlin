package functionalprogramming

import arrow.core.Either
import arrow.core.left
import arrow.core.right

fun parseNumber(s: String): Either<NumberParsingError, Int> {
    return try {
        s.toInt().right()
    } catch (e: NumberFormatException) {
        NumberParsingError("Invalid number format: \"$s\"").left()
    }
}

sealed class NumberParsingError(val message: String)

data class InvalidInput(val input: String) : NumberParsingError("Invalid input: \"$input\"")

fun main() {
    val result1 = parseNumber("123")
    when (result1) {
        is Either.Left -> println("Error: ${result1.value.message}")
        is Either.Right -> println("Success: ${result1.value}")
    }

    val result2 = parseNumber("abc")
    when (result2) {
        is Either.Left -> println("Error: ${result2.value.message}")
        is Either.Right -> println("Success: ${result2.value}")
    }
}
