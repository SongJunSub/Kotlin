package `04-null-safety`

fun main() {
    // 1. Nullable 타입
    var nullableName: String? = "Kotlin"
    // var nonNullableName: String = null // 컴파일 에러

    // 2. 안전한 호출 연산자 (?.)
    println("길이: ${nullableName?.length}")
    nullableName = null
    println("길이 (null일 때): ${nullableName?.length}") // null 출력

    // 3. 엘비스 연산자 (?:)
    val name = nullableName ?: "Guest"
    println("이름: $name")

    // 4. non-null 단언 연산자 (!!) - 주의해서 사용!
    try {
        var mustBeNonNull: String? = "Hello"
        println("단언 연산자: ${mustBeNonNull!!.length}")

        mustBeNonNull = null
        println(mustBeNonNull!!.length) // 여기서 NPE 발생
    } catch (e: NullPointerException) {
        println("NPE 발생: non-null 단언 변수가 null이었습니다.")
    }

    // 5. 안전한 캐스트 (as?)
    val someValue: Any = "123"
    val intValue: Int? = someValue as? Int
    println("안전한 캐스트 결과: $intValue") // null 출력 (String은 Int로 캐스트 불가)
}
