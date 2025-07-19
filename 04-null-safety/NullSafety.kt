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

    // 6. let 함수를 이용한 안전한 호출
    var message: String? = "Hello Kotlin"
    message?.let {
        println("let 함수 내부: $it")
    }

    message = null
    message?.let {
        println("이 메시지는 출력되지 않습니다.")
    }

    // 7. run 함수를 이용한 안전한 호출
    val length = nullableName?.run {
        // 이 블록 안에서는 'this'가 nullableName을 가리키며 non-null 타입으로 처리됨
        println("run 함수 내부: $this")
        length // 'this.length'와 동일
    }
    println("run 함수 결과 (null일 때): $length")
}
