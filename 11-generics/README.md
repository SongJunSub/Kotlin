# 11. 제네릭스 (Generics)

제네릭스는 클래스, 인터페이스, 함수를 정의할 때 타입을 파라미터로 받아, 다양한 타입에 대해 재사용 가능한 코드를 작성할 수 있게 해주는 기능입니다. 이를 통해 타입 안정성을 높이고 코드 중복을 줄일 수 있습니다.

## 주요 개념

- **타입 파라미터 (`<T>`)**: 클래스나 함수 이름 뒤에 꺾쇠괄호(`< >`)를 사용하여 타입 파라미터를 선언합니다. `T`는 관례적으로 많이 사용되는 이름이며, 어떤 이름이든 사용할 수 있습니다.
- **제네릭 클래스/인터페이스**: 다양한 타입의 데이터를 담을 수 있는 컨테이너 클래스를 만들 때 유용합니다. (예: `Box<T>`)
- **제네릭 함수**: 특정 타입에 국한되지 않고 여러 타입에 대해 동작하는 함수를 만들 수 있습니다.
- **타입 제약 (`where`, `:`)**: 타입 파라미터가 특정 클래스를 상속하거나 특정 인터페이스를 구현하도록 제약을 걸 수 있습니다. 이를 통해 해당 타입이 가진 특정 함수나 프로퍼티에 안전하게 접근할 수 있습니다.
- **공변성 (`out`)과 반공변성 (`in`)**: 제네릭 타입 간의 상속 관계를 다루는 고급 개념입니다.
  - `out` (공변성): `Box<Int>`를 `Box<Number>`처럼 다룰 수 있게 합니다. (생산자 역할)
  - `in` (반공변성): `Comparator<Number>`를 `Comparator<Int>`처럼 다룰 수 있게 합니다. (소비자 역할)

제네릭스는 코틀린 표준 라이브러리의 컬렉션(`List<T>`, `Map<K, V>`) 등에서 광범위하게 사용되고 있습니다.

아래 `Generics.kt` 파일에서 실제 예제 코드를 확인할 수 있습니다.
