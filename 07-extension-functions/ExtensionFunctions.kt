package `07-extension-functions`

// String 클래스에 새로운 함수 추가
fun String.addExclamation(): String {
    return "$this!"
}

// Int 클래스에 제곱을 구하는 함수 추가
fun Int.square(): Int {
    return this * this
}

// Nullable String에 대한 안전한 확장 함수
fun String?.getSafeLength(): Int {
    // this가 null이면 0을 반환, 아니면 길이를 반환
    return this?.length ?: 0
}

fun main() {
    val greeting = "Hello, World"
    println(greeting.addExclamation()) // 출력: Hello, World!

    val number = 5
    println("5의 제곱은 ${number.square()} 입니다.") // 출력: 5의 제곱은 25 입니다.

    var nullableString: String? = "Kotlin"
    println("'$nullableString'의 길이: ${nullableString.getSafeLength()}") // 출력: 'Kotlin'의 길이: 6

    nullableString = null
    println("'$nullableString'의 길이: ${nullableString.getSafeLength()}") // 출력: 'null'의 길이: 0
}
