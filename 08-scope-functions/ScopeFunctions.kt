package `08-scope-functions`

import java.util.Random

data class Person(var name: String, var age: Int, var city: String = "Seoul")

fun main() {
    // 1. let: nullable 객체에 안전하게 접근하고, 결과를 반환받을 때
    val name: String? = "Kotlin"
    val length = name?.let {
        println("let 블록 안: $it")
        it.length // 람다의 마지막 표현식이 반환됨
    }
    println("let 결과: $length")

    // 2. apply: 객체 초기화 및 설정에 사용 (객체 자신을 반환)
    val person = Person("Alice", 20).apply {
        // this는 Person 객체를 가리킴
        this.age = 25
        this.city = "Busan"
    }
    println("apply 결과: $person")

    // 3. run: 객체에 대한 연산을 수행하고, 결과를 반환받을 때
    val isAdult = person.run {
        // this는 Person 객체를 가리킴
        age >= 20 // 람다의 마지막 표현식이 반환됨
    }
    println("run 결과: $isAdult")

    // 4. with: non-nullable 객체에 대한 여러 작업을 수행할 때
    val resultString = with(person) {
        "이름: $name, 나이: $age, 도시: $city"
    }
    println("with 결과: $resultString")

    // 5. also: 객체에 부가적인 작업을 수행할 때 (객체 자신을 반환)
    val randomNumber = Random().nextInt(100).also {
        // it은 생성된 난수를 가리킴
        println("생성된 난수: $it")
    }
    // randomNumber는 also 블록의 영향을 받지 않고 원래의 난수 값을 가짐
    println("also 이후: $randomNumber")

    // 6. 스코프 함수 체이닝 예제
    val processedString = "  Kotlin is FUN  "
        .trim() // 앞뒤 공백 제거
        .also { println("트림 후: '$it'") } // 중간 결과 로깅
        .uppercase() // 대문자로 변환
        .let { "처리된 문자열: $it" } // 최종 결과 포맷팅

    println(processedString)
}
