# 10. Sealed 클래스 (Sealed Classes)

Sealed 클래스는 미리 정의된, 제한된 타입의 하위 클래스만 가질 수 있는 특별한 클래스입니다. `enum`과 유사하지만, 하위 클래스가 서로 다른 상태와 데이터를 가질 수 있어 훨씬 유연합니다.

## 주요 특징

- **제한된 상속**: Sealed 클래스의 하위 클래스는 반드시 같은 파일 안에 선언되어야 합니다. (코틀린 1.5부터는 같은 패키지 내에서도 가능)
- **`when` 표현식과의 결합**: `when` 표현식에서 Sealed 클래스의 모든 하위 타입을 처리하면, `else` 절을 생략할 수 있습니다. 컴파일러가 모든 경우를 검사해주므로, 새로운 하위 클래스가 추가되면 `when` 문을 수정하도록 강제하여 코드의 안정성을 높입니다.
- **다양한 상태 표현**: 각 하위 클래스는 자신만의 프로퍼티를 가질 수 있습니다. Sealed 클래스는 제네릭(Generic) 타입을 지원하여 다양한 데이터 타입을 처리할 수 있습니다. 예를 들어, API 응답 결과를 나타낼 때 `Success` 상태는 데이터를, `Error` 상태는 예외 정보를 담을 수 있습니다.

## 활용 사례

- 네트워크 요청의 결과 (Loading, Success, Error)
- 화면의 상태 관리 (UI State)
- 라이브러리에서 정의된 특정 종류의 이벤트 처리

Sealed 클래스를 사용하면 애플리케이션의 상태를 명확하고 타입-안전(type-safe)하게 관리할 수 있습니다.

아래 `SealedClasses.kt` 파일에서 실제 예제 코드를 확인할 수 있습니다.
