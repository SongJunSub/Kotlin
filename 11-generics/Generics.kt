package `11-generics`

// 1. 제네릭 클래스
// 어떤 타입 T의 값이든 담을 수 있는 상자 클래스
class Box<T>(val value: T)

// 2. 제네릭 함수
// 어떤 타입의 배열이든 그 요소를 출력하는 함수
fun <T> printArray(array: Array<T>) {
    println(array.joinToString(", ", "[", "]"))
}

// 3. 타입 제약
// Number의 하위 타입만 처리할 수 있는 제네릭 함수
fun <T : Number> sum(a: T, b: T): Double {
    return a.toDouble() + b.toDouble()
}

// 4. 공변성 (out)
// 읽기만 가능한 제네릭 인터페이스
interface Producer<out T> {
    fun produce(): T
}

class StringProducer : Producer<String> {
    override fun produce(): String = "Hello, Generics!"
}

// 5. 반공변성 (in)
// 쓰기만 가능한 제네릭 인터페이스
interface Consumer<in T> {
    fun consume(value: T)
}

class AnyConsumer : Consumer<Any> {
    override fun consume(value: Any) {
        println("소비: $value")
    }
}

fun main() {
    // 제네릭 클래스 사용
    val intBox = Box(123)
    val stringBox = Box("Kotlin")
    println("Int 상자 내용: ${intBox.value}")
    println("String 상자 내용: ${stringBox.value}")

    // 제네릭 함수 사용
    val intArray = arrayOf(1, 2, 3)
    val stringArray = arrayOf("A", "B", "C")
    print("Int 배열: ")
    printArray(intArray)
    print("String 배열: ")
    printArray(stringArray)

    // 타입 제약이 있는 제네릭 함수 사용
    println("합계 (Int): ${sum(5, 10)}")
    println("합계 (Double): ${sum(3.14, 2.71)}")
    // sum("a", "b") // 컴파일 에러: String은 Number의 하위 타입이 아님

    // 공변성 예제
    val producer: Producer<Any> = StringProducer() // Producer<String>을 Producer<Any>로 할당 가능
    println("공변성 결과: ${producer.produce()}")

    // 반공변성 예제
    val consumer: Consumer<String> = AnyConsumer() // Consumer<Any>를 Consumer<String>으로 할당 가능
    consumer.consume("Kotlin is awesome!")
}
