package `02-functions`

fun main() {
    // 1. 기본 함수 호출
    println("기본 함수: ${add(5, 3)}")

    // 2. 단일 표현식 함수 호출
    println("단일 표현식 함수: ${multiply(5, 3)}")

    // 3. 기본 인자 사용
    greet("Alice") // language 인자 생략
    greet("Bob", "Kotlin")

    // 4. 명명된 인자 사용
    greet(language = "Java", name = "Charlie")

    // 5. 고차 함수와 람다 사용
    val numbers = listOf(1, 2, 3, 4, 5)
    val result = operateOnNumbers(numbers) { a, b -> a + b } // 람다를 마지막 인자로 전달
    println("고차 함수 결과: $result")

    // 람다를 변수에 할당
    val square: (Int) -> Int = { it * it }
    println("람다 변수: ${square(4)}")
}

// 1. 기본 함수
fun add(a: Int, b: Int): Int {
    return a + b
}

// 2. 단일 표현식 함수
fun multiply(a: Int, b: Int) = a * b

// 3. 기본 인자
fun greet(name: String, language: String = "English") {
    println("Hello, $name! Welcome to $language.")
}

// 5. 고차 함수
// (Int, Int) -> Int 타입의 함수를 인자로 받음
fun operateOnNumbers(numbers: List<Int>, operation: (Int, Int) -> Int): Int {
    // 리스트의 모든 요소를 순회하며 연산을 적용
    return numbers.reduce(operation)
}
