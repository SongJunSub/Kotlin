package `05-collections`

fun main() {
    // 1. 불변 List
    val numbers = listOf(1, 2, 3, 4, 5, 2) // 중복 허용
    println("불변 리스트: $numbers")

    // 2. 불변 Set
    val uniqueNumbers = setOf(1, 2, 3, 4, 5, 2) // 중복 자동 제거
    println("불변 세트: $uniqueNumbers")

    // 3. 불변 Map
    val userMap = mapOf("name" to "Alice", "age" to 25)
    println("불변 맵: $userMap")

    // 4. 가변 컬렉션
    val mutableFruits = mutableListOf("Apple", "Banana")
    mutableFruits.add("Cherry")
    println("가변 리스트: $mutableFruits")

    // 5. 컬렉션 처리 함수
    val people = listOf(Person("Alice", 29), Person("Bob", 31), Person("Charlie", 29))

    // filter: 30세 미만인 사람 찾기
    val youngPeople = people.filter { it.age < 30 }
    println("30세 미만: $youngPeople")

    // map: 이름만 추출하기
    val names = people.map { it.name }
    println("이름 목록: $names")

    // forEach: 각 요소에 대해 작업 수행
    println("인사하기:")
    people.forEach { println("Hello, ${it.name}") }

    // groupBy: 나이로 그룹화하기
    val groupedByAge = people.groupBy { it.age }
    println("나이별 그룹: $groupedByAge")
}

data class Person(val name: String, val age: Int)
