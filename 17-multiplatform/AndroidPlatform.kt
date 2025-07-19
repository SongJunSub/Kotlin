package `17-multiplatform`

/**
 * `androidMain` 모듈에 위치할 코틀린 파일입니다.
 * 
 * `commonMain`에 선언된 `expect` 함수를 `actual` 키워드를 사용하여
 * 안드로이드 플랫폼에 맞게 실제로 구현합니다.
 */

// 실제 구현 (Implementation) for Android
actual fun getPlatformName(): String {
    // 안드로이드에서는 Build.VERSION.SDK_INT와 같은 API를 사용하여 더 구체적인 정보를 제공할 수 있습니다.
    return "Android"
}

actual class PlatformLoggerImpl : PlatformLogger {
    override fun log(message: String) {
        println("Android Log: $message")
    }
}
