package `17-multiplatform`

import platform.UIKit.UIDevice

/**
 * `iosMain` 모듈에 위치할 코틀린 파일입니다.
 * 
 * `commonMain`에 선언된 `expect` 함수를 `actual` 키워드를 사용하여
 * iOS 플랫폼에 맞게 실제로 구현합니다.
 */

// 실제 구현 (Implementation) for iOS
actual fun getPlatformName(): String {
    // iOS에서는 UIKit의 UIDevice와 같은 네이티브 프레임워크를 직접 호출할 수 있습니다.
    return UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
