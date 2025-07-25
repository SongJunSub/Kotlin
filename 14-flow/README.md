# 14. Kotlin Flow (고급 비동기 처리)

Kotlin Flow는 코루틴을 기반으로 동작하는 비동기 데이터 스트림입니다. 여러 개의 값을 순차적으로 생성하고 비동기적으로 처리할 수 있도록 설계되었습니다. 리액티브 프로그래밍 패러다임을 코틀린에 맞게 구현한 것으로, RxJava나 Reactor와 유사한 역할을 합니다.

## 주요 개념

- **Flow (플로우)**: 비동기적으로 생성되는 데이터의 스트림입니다. 데이터 소스 역할을 합니다.
- **Producer (생산자)**: `flow { ... }` 빌더 블록 내에서 `emit()` 함수를 호출하여 데이터를 생성하는 주체입니다.
- **Intermediary (중간 연산자)**: 스트림의 데이터를 변환하거나 필터링하는 연산자입니다. (예: `map`, `filter`, `onEach`). 이 연산자들은 그 자체로 스트림을 실행시키지 않고, 새로운 스트림을 반환하는 체이닝(chaining) 구조를 가집니다.
- **Collector (소비자)**: 스트림의 데이터를 최종적으로 소비하는 주체입니다. `collect()`와 같은 터미널(terminal) 연산자가 호출될 때 비로소 플로우가 동작하기 시작합니다. (Cold Stream의 특징)
- **StateFlow**: `MutableStateFlow`를 통해 상태를 발행하고, `StateFlow`를 통해 상태를 관찰하는 Hot Flow입니다. 항상 최신 상태 값을 가집니다.
- **SharedFlow**: `MutableSharedFlow`를 통해 이벤트를 발행하고, `SharedFlow`를 통해 이벤트를 관찰하는 Hot Flow입니다. 여러 소비자에게 이벤트를 공유할 수 있습니다.

## Flow의 특징

- **Cold Stream**: `collect()`가 호출되기 전까지는 아무런 동작도 하지 않습니다. 각 `collect()` 호출마다 새로운 데이터 스트림이 생성됩니다.
- **구조적 동시성**: 코루틴 스코프 내에서 실행되므로, 스코프가 취소되면 플로우도 함께 자동으로 취소되어 리소스 누수를 방지합니다.
- **Suspend 함수 활용**: 플로우의 모든 연산자는 `suspend` 함수를 지원하므로, 비동기 코드를 자연스럽게 통합할 수 있습니다.

## 설정 방법

Flow를 사용하려면 `kotlinx-coroutines-core` 라이브러리 의존성이 필요합니다. (기존 코루틴 예제에서 이미 설정했다면 추가 설정은 필요 없습니다.)

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0") // 버전은 최신으로
}
```

아래 `Flow.kt` 파일에서 실제 예제 코드를 확인할 수 있습니다.
