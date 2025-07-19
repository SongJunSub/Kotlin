# 18. Jetpack Compose (Android UI 개발)

Jetpack Compose는 안드로이드 UI를 선언적으로 구축하기 위한 현대적인 툴킷입니다. XML 레이아웃 대신 코틀린 코드를 사용하여 UI를 직접 작성하며, 상태(State) 변화에 따라 UI가 자동으로 업데이트되는 반응형 프로그래밍 모델을 채택하고 있습니다.

## 주요 개념

- **선언형 UI**: UI의 모습을 코드로 직접 기술하며, 데이터가 변경되면 UI가 자동으로 다시 그려집니다.
- **컴포저블 함수 (Composable Functions)**: `@Composable` 어노테이션이 붙은 함수로, UI의 일부분을 정의합니다. 이 함수들은 상태가 변경될 때 자동으로 재구성(recompose)됩니다.
- **상태 (State)**: UI의 동적인 부분을 나타내는 데이터입니다. `remember`와 `mutableStateOf`를 사용하여 상태를 관리하고, 상태가 변경될 때 UI가 업데이트되도록 합니다.
- **Modifier**: 컴포저블의 크기, 레이아웃, 동작, 모양 등을 변경하는 데 사용됩니다.
- **Preview**: `@Preview` 어노테이션을 사용하여 에뮬레이터나 실제 기기 없이도 안드로이드 스튜디오에서 컴포저블의 UI를 미리 볼 수 있습니다.

## 설정 방법 (`build.gradle.kts`)

Jetpack Compose를 사용하려면 모듈의 `build.gradle.kts` 파일에 Compose 관련 의존성을 추가해야 합니다.

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // ...
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // 사용 중인 코틀린 버전에 맞는 Compose 컴파일러 확장 버전
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.08.00")) // Compose BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

아래 `MainActivity.kt` 파일에서 간단한 카운터 앱 예제를 확인할 수 있습니다. 이 예제는 `mutableStateOf`와 `remember`를 사용하여 버튼 클릭 시 숫자가 증가하는 상태를 관리하는 방법을 보여줍니다.
