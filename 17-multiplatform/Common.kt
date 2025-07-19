package `17-multiplatform`

/**
 * `commonMain` 모듈에 위치할 코틀린 파일입니다.
 * 
 * 플랫폼의 고유한 정보를 가져오는 기능을 `expect` 키워드로 선언합니다.
 * 이 함수의 실제 구현은 각 플랫폼별 모듈(`androidMain`, `iosMain` 등)에 `actual`로 존재해야 합니다.
 */

// 기대 선언 (Declaration)
expect fun getPlatformName(): String

// 공통 로직
class Greeter {
    fun greet(): String {
        return "Hello, ${getPlatformName()}!"
    }
}

// 공통 데이터 클래스
data class User(val id: String, val name: String)

// 플랫폼별 구현이 필요한 인터페이스
expect interface PlatformLogger {
    fun log(message: String)
}
