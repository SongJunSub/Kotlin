# 19. 함수형 프로그래밍 (Functional Programming)

코틀린은 객체 지향 언어이지만, 함수형 프로그래밍 패러다임을 강력하게 지원합니다. 특히 `Arrow`와 같은 라이브러리를 활용하면 순수 함수, 불변성, 고차 함수 등을 통해 더 안전하고 예측 가능한 코드를 작성할 수 있습니다.

## 주요 개념

- **순수 함수 (Pure Functions)**: 동일한 입력에 대해 항상 동일한 출력을 반환하며, 외부 상태를 변경하지 않고 외부 상태에 의존하지 않는 함수입니다.
- **불변성 (Immutability)**: 데이터가 한 번 생성되면 변경되지 않는 것을 의미합니다. 이는 동시성 문제를 줄이고 코드의 예측 가능성을 높입니다.
- **고차 함수 (Higher-Order Functions)**: 함수를 인자로 받거나 함수를 반환하는 함수입니다.
- **데이터 변환**: `map`, `filter`, `fold`, `reduce` 등 컬렉션에 대한 함수형 연산을 통해 데이터를 변환합니다.

## Arrow 라이브러리

`Arrow`는 코틀린을 위한 강력한 함수형 프로그래밍 라이브러리입니다. `Option`, `Either`, `Try`와 같은 데이터 타입을 제공하여 널(null)이나 예외 처리와 같은 일반적인 문제를 함수형 방식으로 해결할 수 있도록 돕습니다.

### `Option`

`Option<T>`는 값이 존재할 수도 있고(`Some(value)`), 존재하지 않을 수도 있음(`None`)을 명시적으로 나타내는 타입입니다. 널 안정성을 강화하고 NPE(NullPointerException)를 방지하는 데 유용합니다.

### `Either`

`Either<L, R>`는 두 가지 가능한 결과 중 하나를 나타내는 타입입니다. 일반적으로 `Left`는 실패(에러)를, `Right`는 성공(값)을 나타냅니다. 예외 처리 대신 명시적인 에러 처리를 강제하여 코드의 견고함을 높입니다.

### `Try`

`Try<T>`는 계산이 성공했는지(`Success(value)`) 또는 실패했는지(`Failure(exception)`)를 나타내는 타입입니다. 예외를 값으로 캡슐화하여 함수형 방식으로 예외를 처리할 수 있게 합니다.

## 설정 방법 (`build.gradle.kts`)

```kotlin
dependencies {
    implementation("io.arrow-kt:arrow-core:1.2.0") // 버전은 최신으로
}
```

아래 `OptionExample.kt`, `EitherExample.kt`, `TryExample.kt` 파일에서 `Arrow` 라이브러리를 활용한 함수형 프로그래밍 예제를 확인할 수 있습니다.
