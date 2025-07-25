# 20. Kotlin DSL (Domain-Specific Language)

코틀린 DSL(Domain-Specific Language)은 코틀린의 강력한 문법 기능을 활용하여 특정 도메인에 특화된 언어를 만드는 것을 의미합니다. 이를 통해 코드가 해당 도메인의 개념을 더 자연스럽게 표현하고, 가독성을 높이며, 개발 생산성을 향상시킬 수 있습니다.

## 핵심 원리

코틀린 DSL은 주로 다음 기능들을 조합하여 구현됩니다.

- **수신 객체 지정 람다 (Lambdas with Receiver)**: 람다 내부에서 특정 객체의 컨텍스트를 `this`로 사용할 수 있게 하여, 마치 해당 객체의 메서드를 호출하는 것처럼 코드를 작성할 수 있게 합니다. 이는 중첩된 구조를 표현하는 데 매우 유용합니다.
- **확장 함수 (Extension Functions)**: 기존 클래스에 새로운 함수를 추가하여 DSL의 API를 확장합니다.
- **`@DslMarker` 어노테이션**: DSL 내부에서 의도치 않은 스코프의 함수 호출을 방지하여, DSL의 타입 안전성을 강화합니다.
- **중위 호출 (Infix Functions)**: 함수 호출 시 점(.)과 괄호()를 생략하고 `a func b` 형태로 호출할 수 있게 하여, 자연어와 유사한 표현을 가능하게 합니다.
- **단항 연산자 오버로딩 (Unary Operator Overloading)**: `+`와 같은 단항 연산자를 오버로딩하여 DSL의 표현력을 높입니다. (예: `+ "text"`)

## 왜 유용한가?

- **가독성**: 도메인에 특화된 용어를 사용하여 코드가 자연어처럼 읽힙니다.
- **생산성**: 반복적인 코드 작성을 줄이고, 더 적은 코드로 더 많은 작업을 수행할 수 있습니다.
- **타입 안전성**: 컴파일 시점에 오류를 감지하여 런타임 오류를 줄입니다.

## 대표적인 사용 사례

- **Gradle Kotlin DSL**: `build.gradle.kts` 파일에서 빌드 스크립트를 작성하는 데 사용됩니다.
- **HTML/XML 빌더**: HTML 또는 XML 구조를 선언적으로 생성하는 데 사용됩니다.
- **SQL 쿼리 빌더**: SQL 쿼리를 타입 안전하게 작성하는 데 사용됩니다.
- **UI 프레임워크**: Jetpack Compose와 같은 선언형 UI 프레임워크의 기반이 됩니다.

아래 `HtmlBuilder.kt` 파일에서는 간단한 HTML 구조를 만드는 DSL 예제를 확인할 수 있습니다.
