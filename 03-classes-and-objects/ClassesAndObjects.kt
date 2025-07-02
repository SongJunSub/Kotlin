package `03-classes-and-objects`

// 1. 기본 클래스 선언
class Person(val name: String, var age: Int) {
    // 초기화 블록
    init {
        println("$name, $age 살의 Person 객체 생성")
    }

    fun introduce() {
        println("안녕하세요, 저는 $name 이고 $age 살입니다.")
    }
}

// 2. 데이터 클래스
data class User(val id: Long, val name: String)

// 3. 상속을 위한 open 클래스
open class Animal(val name: String) {
    open fun speak() {
        println("$name 이(가) 소리를 냅니다.")
    }
}

// Animal 클래스를 상속받는 Dog 클래스
class Dog(name: String) : Animal(name) {
    override fun speak() {
        println("$name 이(가) 멍멍! 하고 짖습니다.")
    }
}

// 4. 인터페이스
interface Movable {
    fun move()
    fun stop() {
        println("멈춥니다.")
    }
}

class Car(val name: String) : Movable {
    override fun move() {
        println("$name 이(가) 움직입니다.")
    }
}

// 5. 싱글턴 객체
object DatabaseManager {
    fun connect() {
        println("데이터베이스에 연결되었습니다.")
    }
}

fun main() {
    // 클래스 인스턴스 생성
    val person = Person("JunSub", 30)
    person.introduce()

    // 데이터 클래스 사용
    val user1 = User(1, "Alice")
    val user2 = User(1, "Alice")
    println("데이터 클래스 비교: ${user1 == user2}") // true (내용 기반 비교)
    println(user1.toString())

    // 상속과 오버라이딩
    val dog = Dog("해피")
    dog.speak()

    // 인터페이스 구현
    val car = Car("소나타")
    car.move()
    car.stop()

    // 싱글턴 객체 사용
    DatabaseManager.connect()
}
