package `12-delegated-properties`

import kotlin.properties.Delegates

// 1. lazy: 지연 초기화
// 이 프로퍼티는 처음 접근될 때 초기화 코드가 실행됩니다.
val lazyValue: String by lazy {
    println("lazyValue가 초기화되었습니다.")
    "Hello, lazy!"
}

class User {
    // 2. observable: 값 변경 감지
    // 이름이 변경될 때마다 메시지를 출력합니다.
    var name: String by Delegates.observable("<이름 없음>") { prop, old, new ->
        println("${prop.name}이(가) '$old'에서 '$new'(으)로 변경되었습니다.")
    }

    // 3. vetoable: 값 변경 조건 부여
    // 나이는 0 이상일 때만 변경을 허용합니다.
    var age: Int by Delegates.vetoable(0) { prop, old, new ->
        if (new >= 0) {
            println("${prop.name}을(를) $new 살로 설정합니다.")
            true // 변경 승인
        } else {
            println("나이는 음수일 수 없습니다. ($new 거부)")
            false // 변경 거부
        }
    }
}

fun main() {
    println("main 함수 시작")
    println(lazyValue) // 이 시점에 lazyValue가 초기화됨
    println(lazyValue) // 이미 초기화되었으므로 다시 실행되지 않음

    println("\n--- observable 예제 ---")
    val user = User()
    user.name = "Alice"
    user.name = "Bob"

    println("\n--- vetoable 예제 ---")
    user.age = 25
    println("현재 나이: ${user.age}")
    user.age = -5 // 이 변경은 거부됨
    println("현재 나이: ${user.age}") // 여전히 25
}