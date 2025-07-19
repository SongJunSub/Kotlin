package `10-sealed-classes`

// API 요청 결과를 나타내는 Sealed 클래스
sealed class Result<out T> {
    // 성공 시 데이터를 포함하는 데이터 클래스
    data class Success<out T>(val data: T) : Result<T>()

    // 실패 시 에러 메시지를 포함하는 클래스
    data class Error(val message: String) : Result<Nothing>()

    // 로딩 상태를 나타내는 객체
    object Loading : Result<Nothing>()
}

fun <T> handleResult(result: Result<T>) {
    when (result) {
        is Result.Success -> {
            println("요청 성공: ${result.data}")
        }
        is Result.Error -> {
            println("에러 발생: ${result.message}")
        }
        is Result.Loading -> {
            println("로딩 중...")
        }
        // Sealed 클래스의 모든 하위 타입을 처리했으므로 else가 필요 없음
    }
}

fun main() {
    val successResult = Result.Success("데이터를 성공적으로 가져왔습니다.")
    val errorResult = Result.Error("네트워크 연결이 끊어졌습니다.
")
    val loadingResult = Result.Loading

    handleResult(successResult)
    handleResult(errorResult)
    handleResult(loadingResult)

    val intSuccess = Result.Success(123)
    handleResult(intSuccess)
}
