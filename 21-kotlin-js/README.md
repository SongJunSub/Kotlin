# 21. Kotlin/JS (웹 프론트엔드 개발)

Kotlin/JS는 코틀린 코드를 자바스크립트로 컴파일하여 웹 프론트엔드 애플리케이션을 개발할 수 있게 해주는 기술입니다. 이를 통해 웹 개발에서도 코틀린의 강력한 타입 시스템과 생산성을 활용할 수 있습니다.

## 주요 개념

- **코틀린 코드 -> 자바스크립트 컴파일**: 작성된 코틀린 코드는 웹 브라우저에서 실행될 수 있는 자바스크립트 코드로 변환됩니다.
- **DOM 조작**: `kotlinx.browser` 패키지를 통해 웹 브라우저의 DOM(Document Object Model)에 접근하고 조작할 수 있습니다. (예: `document.createElement`, `element.appendChild`)
- **외부 자바스크립트 라이브러리 연동**: `external` 키워드를 사용하여 기존의 자바스크립트 라이브러리(React, Vue, jQuery 등)를 코틀린 코드에서 타입 안전하게 사용할 수 있습니다.
- **Webpack 통합**: 개발 서버 실행, 번들링, 코드 최적화 등을 위해 Webpack과 통합되어 있습니다.

## 설정 방법 (`build.gradle.kts`)

Kotlin/JS 프로젝트를 설정하려면 `kotlin("js")` 플러그인을 사용하고, `browser` 또는 `nodejs` 타겟을 설정해야 합니다.

```kotlin
plugins {
    kotlin("js") version "1.9.20" // 코틀린 버전
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0") // HTML DSL 사용 시
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0") // 코루틴 사용 시
            }
        }
    }
}

// 웹팩 개발 서버 실행
tasks.register<Copy>("copyWebpackResources") {
    from(project.projectDir.resolve("src/main/resources"))
    into(project.buildDir.resolve("distributions"))
}

tasks.named<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>("run") {
    dependsOn("copyWebpackResources")
}
```

아래 `Main.kt` 파일에서 간단한 DOM 조작 및 동적 리스트 생성 예제를 확인할 수 있습니다.
