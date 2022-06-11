data class Chat(
    val id : Int,
    val firstUserId : Int,
    val secondUserId : Int,
    val messageList : ArrayList<Message> = ArrayList()
)