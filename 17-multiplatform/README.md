# 17. 코틀린 멀티플랫폼 (Kotlin Multiplatform - KMP)

코틀린 멀티플랫폼(KMP)은 안드로이드, iOS, 웹, 데스크톱 등 여러 플랫폼에서 동일한 비즈니스 로직을 공유할 수 있게 해주는 강력한 기술입니다. 코드를 한 번만 작성하여 여러 플랫폼에서 재사용함으로써 개발 생산성을 획기적으로 높일 수 있습니다.

## 핵심 개념

- **공통 모듈 (`commonMain`)**: 플랫폼에 독립적인 순수 코틀린 코드를 작성하는 곳입니다. 이곳에 작성된 코드는 모든 타겟 플랫폼에서 공유됩니다. (예: 데이터 클래스, 비즈니스 로직, API 인터페이스)
- **플랫폼별 모듈 (`androidMain`, `iosMain`, `jsMain` 등)**: 각 플랫폼의 고유한 API나 기능에 접근해야 할 때 사용됩니다. 공통 모듈에서 정의한 `expect` 선언을 각 플랫폼에 맞게 `actual` 구현으로 완성하는 역할을 합니다.
- **`expect`와 `actual` 선언**: 공통 모듈에서 `expect` 키워드로 특정 기능(클래스, 함수, 프로퍼티 등)의 구현을 "기대"한다고 선언하면, 각 플랫폼별 모듈에서는 `actual` 키워드를 사용하여 해당 기능을 플랫폼에 맞게 실제로 구현해야 합니다. 이를 통해 공통 코드가 플랫폼별 기능을 호출할 수 있게 됩니다.
- **공통 데이터 클래스**: 모든 플랫폼에서 공유되는 데이터 구조를 정의할 수 있습니다.
- **플랫폼별 인터페이스 구현**: 공통 모듈에서 `expect`로 선언된 인터페이스를 각 플랫폼에서 `actual` 클래스로 구현하여 플랫폼별 동작을 정의할 수 있습니다.

## 일반적인 프로젝트 구조

KMP 프로젝트는 보통 다음과 같은 디렉토리 구조를 가집니다.

```
.
├── build.gradle.kts
├── settings.gradle.kts
└── shared/                  # 공통 로직을 담는 모듈
    ├── build.gradle.kts
    └── src/
        ├── commonMain/
        │   └── kotlin/
        │       └── com/example/project/Common.kt
        ├── androidMain/
        │   └── kotlin/
        │       └── com/example/project/Platform.kt
        └── iosMain/
            └── kotlin/
                └── com/example/project/Platform.kt
```

## 설정 방법 (`build.gradle.kts`)

KMP 프로젝트 설정은 일반 프로젝트보다 복잡하며, `kotlin-multiplatform` 플러그인을 사용합니다. 타겟으로 할 플랫폼(android, ios, jvm 등)을 명시하고 각 소스셋(`sourceSets`)의 의존성을 설정해야 합니다.

```kotlin
// shared/build.gradle.kts 예시
plugins {
    kotlin("multiplatform")
    // ... (android, compose 등 다른 플러그인)
}

kotlin {
    // 타겟 플랫폼 정의
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // 공통 라이브러리 (예: Coroutines, Ktor, SQLDelight)
            }
        }
        val androidMain by getting { ... }
        val iosMain by creating { ... }
    }
}
```

아래 파일들은 `expect`/`actual` 패턴을 보여주는 간단한 예제입니다.
