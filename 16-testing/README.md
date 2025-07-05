# 16. 코틀린 테스트 (Testing with Kotest & MockK)

소프트웨어의 안정성을 보장하기 위해 테스트 코드를 작성하는 것은 매우 중요합니다. 코틀린 생태계에는 테스트를 더 쉽고 표현력 있게 만들어주는 훌륭한 라이브러리들이 있습니다. 여기서는 `Kotest`와 `MockK`를 소개합니다.

## Kotest: 더 나은 테스트 프레임워크

`Kotest`는 JUnit을 대체하거나 보완할 수 있는 강력한 테스트 프레임워크입니다. 서술적인 테스트 구조, 다양한 매처(matcher), 프로퍼티 기반 테스트 등 많은 고급 기능을 제공합니다.

### 주요 특징
- **다양한 테스트 스타일**: `BehaviorSpec`(BDD 스타일), `StringSpec`(간단한 스타일) 등 원하는 방식으로 테스트를 구조화할 수 있습니다.
- **풍부한 단언(Assertions)**: `shouldBe`, `shouldNotBe`, `shouldThrow` 등 직관적인 단언 함수를 제공하여 테스트 코드를 읽기 쉽게 만듭니다.
- **데이터 기반 테스트**: 여러 다른 입력 값에 대해 동일한 테스트를 쉽게 반복 실행할 수 있습니다.

## MockK: 코틀린을 위한 모킹 라이브러리

`MockK`는 코틀린으로 만들어진 모킹(mocking) 라이브러리입니다. 테스트하려는 대상이 의존하는 객체(의존성)를 가짜(mock) 객체로 만들어, 특정 상황을 시뮬레이션하고 의존성을 외부 환경으로부터 격리시킬 수 있습니다.

### 주요 특징
- **코틀린 친화적**: 코틀린의 `suspend` 함수, 확장 함수, `object` 등 다양한 기능을 완벽하게 지원합니다.
- **간결한 API**: `every`, `returns`, `verify` 등 간단하고 직관적인 API로 모의 객체의 행동을 정의하고 검증할 수 있습니다.

## 설정 방법 (`build.gradle.kts`)

```kotlin
dependencies {
    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")

    // MockK
    testImplementation("io.mockk:mockk:1.13.8")
}

// 테스트 실행을 위해 추가
tasks.withType<Test> {
    useJUnitPlatform()
}
```

아래 `UserServiceTest.kt` 파일에서 `Kotest`와 `MockK`를 사용한 테스트 예제를 확인할 수 있습니다.
