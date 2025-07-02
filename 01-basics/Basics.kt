package `01-basics`

fun main() {
    // 1. 변수 선언
    val immutableValue: String = "Hello, Kotlin!" // 불변 변수 (읽기 전용)
    var mutableValue: Int = 10 // 가변 변수
    mutableValue = 20 // 값 변경 가능

    println("불변 변수: $immutableValue")
    println("가변 변수: $mutableValue")

    // 2. 데이터 타입 및 타입 추론
    val language = "Kotlin" // 타입 추론 (String)
    val year = 2024 // 타입 추론 (Int)

    println("$language은 $year년에 시작되었습니다.")

    // 3. 문자열 템플릿
    val name = "사용자"
    println("안녕하세요, $name 님!")
    println("1 + 1 = ${1 + 1}")

    // 4. 조건문 (if)
    val score = 85
    val grade = if (score >= 90) {
        "A"
    } else if (score >= 80) {
        "B"
    } else {
        "C"
    }
    println("학점: $grade")

    // 5. 조건문 (when)
    val day = 3
    val dayString = when (day) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        else -> "기타"
    }
    println("오늘은 $dayString 입니다.")

    // 6. 반복문 (for)
    println("1부터 5까지 출력:")
    for (i in 1..5) {
        print("$i ")
    }
    println()

    // 7. 반복문 (while)
    var count = 5
    println("카운트다운:")
    while (count > 0) {
        print("$count ")
        count--
    }
    println()
}
