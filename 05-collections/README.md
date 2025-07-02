# 5. 컬렉션 (Collections)

코틀린은 자바 컬렉션 프레임워크를 확장하여 더 강력하고 사용하기 쉬운 API를 제공합니다.

## 주요 개념

- **불변 컬렉션 (Immutable Collections)**: `listOf`, `setOf`, `mapOf`를 사용하여 생성하며, 한 번 생성되면 요소를 추가, 수정, 삭제할 수 없습니다.
- **가변 컬렉션 (Mutable Collections)**: `mutableListOf`, `mutableSetOf`, `mutableMapOf`를 사용하여 생성하며, 생성 후에도 요소를 변경할 수 있습니다.
- **컬렉션 처리 함수**: `filter`, `map`, `forEach`, `groupBy` 등 데이터를 쉽게 가공하고 처리할 수 있는 다양한 고차 함수를 제공합니다.

### 컬렉션 종류

- **List**: 순서가 있는 요소의 집합. 중복을 허용합니다.
- **Set**: 순서가 없는 고유한 요소의 집합. 중복을 허용하지 않습니다.
- **Map**: 키(key)와 값(value)의 쌍으로 이루어진 집합. 키는 고유해야 합니다.

아래 `Collections.kt` 파일에서 실제 예제 코드를 확인할 수 있습니다.
