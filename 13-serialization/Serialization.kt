package `13-serialization`

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// 직렬화가 가능하도록 @Serializable 어노테이션 추가
@Serializable
data class User(
    val id: Long,
    
    // JSON에서는 "user_name"으로, 코틀린에서는 "name"으로 사용
    @SerialName("user_name")
    val name: String,
    
    // 이 필드는 직렬화/역직렬화 과정에서 제외됨
    @Transient
    val password: String = "secret",
    
    // null 값을 가질 수 있는 옵셔널 필드
    val email: String? = null
)

@Serializable
data class Event(
    val name: String,
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate
)

// LocalDate를 위한 커스텀 직렬화/역직렬화
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }
}

fun main() {
    val user = User(1, "SongJunSub", email = "junsub@example.com")

    // 1. 객체를 JSON 문자열로 인코딩 (직렬화)
    val jsonString = Json.encodeToString(user)
    println("직렬화된 JSON: $jsonString")
    // 출력: 직렬화된 JSON: {"id":1,"user_name":"SongJunSub","email":"junsub@example.com"}
    // password 필드는 @Transient 어노테이션 때문에 제외됨

    // 2. JSON 문자열을 객체로 디코딩 (역직렬화)
    val jsonToDecode = "{"id":2,"user_name":"Kotlin","email":null}"
    val decodedUser = Json.decodeFromString<User>(jsonToDecode)
    println("역직렬화된 객체: $decodedUser")
    // 출력: 역직렬화된 객체: User(id=2, name=Kotlin, password=secret, email=null)
    // password는 기본값 "secret"으로 채워짐
    
    // 예쁜 형식으로 출력하기
    val prettyJson = Json { prettyPrint = true }
    val prettyJsonString = prettyJson.encodeToString(user)
    println("\n예쁘게 출력된 JSON:\n$prettyJsonString")

    // 3. 커스텀 직렬화/역직렬화 예제 (LocalDate)
    val event = Event("Kotlin Meetup", LocalDate.of(2024, 7, 19))
    val eventJson = Json.encodeToString(event)
    println("\n직렬화된 Event: $eventJson")

    val decodedEvent = Json.decodeFromString<Event>(eventJson)
    println("역직렬화된 Event: $decodedEvent")
}
