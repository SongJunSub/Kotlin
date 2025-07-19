package functionalprogramming

import arrow.core.Try
import arrow.core.failure
import arrow.core.success

fun divide(a: Int, b: Int): Try<Int> {
    return Try {
        a / b
    }
}

fun main() {
    val result1 = divide(10, 2)
    when (result1) {
        is Try.Success -> println("Success: ${result1.value}")
        is Try.Failure -> println("Error: ${result1.exception.message}")
    }

    val result2 = divide(10, 0)
    when (result2) {
        is Try.Success -> println("Success: ${result2.value}")
        is Try.Failure -> println("Error: ${result2.exception.message}")
    }

    // Using map and recover
    val safeDivision = divide(20, 4)
        .map { it * 2 } // 성공 시 값 변환
        .recover { 0 } // 실패 시 기본값 제공
    println("Safe division (success): $safeDivision")

    val failedDivision = divide(20, 0)
        .map { it * 2 }
        .recover { 0 }
    println("Safe division (failure): $failedDivision")
}
