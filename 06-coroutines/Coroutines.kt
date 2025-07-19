package `06-coroutines`

import kotlinx.coroutines.*

fun main() = runBlocking { // main 함수에서 코루틴을 시작하고 완료될 때까지 대기
    println("메인 프로그램 시작")

    // 1. launch: 결과를 반환하지 않는 코루틴
    val job: Job = launch {
        println("launch 코루틴 시작")
        delay(1000L) // 1초간 일시 중단
        println("launch 코루틴 종료")
    }

    // 2. async: 결과를 반환하는 코루틴
    val deferredResult: Deferred<String> = async {
        println("async 코루틴 시작")
        delay(2000L) // 2초간 일시 중단
        "코루틴 결과"
    }

    println("다른 작업 수행 중...")

    // async의 결과 기다리기
    val result = deferredResult.await()
    println("async 결과: $result")

    // launch가 끝날 때까지 기다리기 (선택 사항)
    job.join()

    println("메인 프로그램 종료")

    // 3. withContext: 디스패처 변경
    println("\nwithContext 예제 시작")
    val resultFromBackground = withContext(Dispatchers.Default) {
        println("백그라운드 작업 시작 (스레드: ${Thread.currentThread().name})")
        delay(1500L)
        "백그라운드 작업 완료"
    }
    println("백그라운드 작업 결과: $resultFromBackground (스레드: ${Thread.currentThread().name})")
    println("withContext 예제 종료")
}
