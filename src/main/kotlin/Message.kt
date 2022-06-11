import java.time.LocalDateTime

data class Message(
    val chatId : Int,
    val messageId : Int,
    val authorId : Int,
    val anotherUserId : Int,
    val text : String,
    val time : LocalDateTime = LocalDateTime.now(),
    val read : Boolean = false
)