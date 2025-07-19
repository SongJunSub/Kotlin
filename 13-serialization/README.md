# 13. 데이터 직렬화 (Serialization)

데이터 직렬화는 코틀린 객체를 JSON과 같은 다른 형식으로 변환(인코딩)하거나, 그 반대로 변환(디코딩)하는 과정을 말합니다. 코틀린에서는 `kotlinx.serialization` 라이브러리를 통해 이 과정을 쉽고 타입-안전하게 처리할 수 있습니다.

## 왜 필요한가?

- **API 통신**: 서버와 클라이언트가 JSON 형식으로 데이터를 주고받을 때
- **데이터 저장**: 객체 상태를 파일이나 데이터베이스에 영구적으로 저장할 때
- **플랫폼 간 데이터 공유**: 서로 다른 시스템 간에 표준화된 형식으로 데이터를 교환할 때

## `kotlinx.serialization` 설정 방법

`kotlinx.serialization`을 사용하려면 빌드 스크립트(예: `build.gradle.kts`)에 플러그인과 라이브러리 의존성을 추가해야 합니다.

**1. 플러그인 추가 (`plugins` 블록):**
```kotlin
plugins {
    kotlin("jvm") version "1.9.20" // 코틀린 버전
    kotlin("plugin.serialization") version "1.9.20" // 직렬화 플러그인
}
```

**2. 의존성 추가 (`dependencies` 블록):**
```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}
```

## 사용 방법

1.  **`@Serializable` 어노테이션**: 직렬화할 `data class` 위에 `@Serializable` 어노테이션을 붙입니다.
2.  **`Json` 객체**: `Json` 객체를 사용하여 인코딩(`encodeToString`)과 디코딩(`decodeFromString`)을 수행합니다.

### 주요 어노테이션

- **`@SerialName`**: JSON 필드 이름과 코틀린 프로퍼티 이름을 다르게 매핑하고 싶을 때 사용합니다.
- **`@Transient`**: 특정 프로퍼티를 직렬화 과정에서 제외하고 싶을 때 사용합니다.

- **`@Transient`**: 특정 프로퍼티를 직렬화 과정에서 제외하고 싶을 때 사용합니다.
- **`KSerializer`**: 복잡한 타입이나 외부 라이브러리 타입을 직렬화/역직렬화하기 위한 커스텀 로직을 정의할 때 사용합니다.

아래 `Serialization.kt` 파일에서 실제 예제 코드를 확인할 수 있습니다.
