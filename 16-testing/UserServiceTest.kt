package `16-testing`

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

// --- 테스트 대상 클래스 ---

// 사용자 정보를 가져오는 역할을 하는 인터페이스 (의존성)
interface UserRepository {
    fun findById(id: Int): User?
    fun save(user: User)
}

// 사용자 정보를 담는 데이터 클래스
data class User(val id: Int, var name: String, var email: String)

// 비즈니스 로직을 처리하는 서비스 클래스
class UserService(private val userRepository: UserRepository) {
    fun getUserName(id: Int): String {
        val user = userRepository.findById(id)
        return user?.name ?: "User Not Found"
    }

    fun updateUserEmail(id: Int, newEmail: String): Boolean {
        val user = userRepository.findById(id)
        if (user != null) {
            user.email = newEmail
            userRepository.save(user)
            return true
        }
        return false
    }
}

// --- Kotest와 MockK를 사용한 테스트 코드 ---

class UserServiceTest : BehaviorSpec({

    // 가짜 UserRepository 객체 생성
    val userRepository = mockk<UserRepository>()
    // 테스트 대상인 UserService 객체 생성
    val userService = UserService(userRepository)

    // BDD (행위 주도 개발) 스타일로 테스트 작성
    Given("사용자 ID가 주어졌을 때") {
        val userId = 1
        val user = User(userId, "Alice", "alice@example.com")

        When("데이터베이스에 해당 사용자가 존재하면") {
            // userRepository.findById(1)이 호출되면 user 객체를 반환하도록 설정
            every { userRepository.findById(userId) } returns user

            Then("사용자 이름을 반환해야 한다") {
                val userName = userService.getUserName(userId)
                userName shouldBe "Alice"
            }
        }

        When("데이터베이스에 해당 사용자가 존재하지 않으면") {
            // userRepository.findById(1)이 호출되면 null을 반환하도록 설정
            every { userRepository.findById(userId) } returns null

            Then("'User Not Found'를 반환해야 한다") {
                val userName = userService.getUserName(userId)
                userName shouldBe "User Not Found"
            }
        }
    }
    
    Given("사용자 이메일 변경을 시도할 때") {
        val userId = 2
        val newEmail = "bob_new@example.com"
        val user = User(userId, "Bob", "bob@example.com")
        
        When("사용자가 존재하면") {
            every { userRepository.findById(userId) } returns user
            every { userRepository.save(any()) } returns Unit // save 함수는 아무것도 반환하지 않음
            
            val result = userService.updateUserEmail(userId, newEmail)
            
            Then("결과는 true여야 한다") {
                result shouldBe true
            }
            
            Then("사용자의 이메일이 변경되어야 한다") {
                user.email shouldBe newEmail
            }
            
            Then("save 함수가 한 번 호출되어야 한다") {
                verify(exactly = 1) { userRepository.save(user) }
            }
        }
    }
})
