package `14-flow`

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// 1. Flow 빌더를 사용하여 1초마다 숫자를 생성하는 Flow
fun simpleFlow(): Flow<Int> = flow {
    println("Flow가 시작되었습니다.")
    for (i in 1..3) {
        delay(1000) // 1초 대기
        emit(i)     // 데이터 발생
    }
}

fun main() = runBlocking {
    println("main 함수 시작")

    // --- 기본 Flow 예제 ---
    println("\n--- 기본 Flow 예제 ---")
    val flow = simpleFlow()
    println("collect() 호출 전")
    // collect()를 호출해야 Flow가 동작 시작 (Cold Stream)
    flow.collect { value ->
        println("수신: $value")
    }
    println("collect() 호출 후")

    // --- 중간 연산자 예제 ---
    println("\n--- 중간 연산자 예제 ---")
    println("Flow를 처리합니다...")
    (1..5).asFlow() // 1부터 5까지의 숫자를 가진 Flow 생성
        .filter { it % 2 == 0 } // 짝수만 필터링
        .map { "숫자 $it" } // 문자열로 변환
        .collect { result ->
            println(result)
        }

    // --- Flow의 비동기적 특성 예제 ---
    println("\n--- 비동기 특성 예제 ---")
    val time = measureTimeMillis {
        flowOf("A", "B", "C")
            .onEach { delay(300) } // 각 요소를 처리하기 전에 딜레이
            .collect { println(it) }
    }
    println("총 소요 시간: ${time}ms")

    println("\nmain 함수 종료")
}

// 시간 측정을 위한 헬퍼 함수
suspend fun measureTimeMillis(block: suspend () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
