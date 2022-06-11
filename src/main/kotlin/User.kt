import ChatService.addMessage
import ChatService.deleteChat
import ChatService.deleteMessage
import ChatService.getListChats
import ChatService.readMessage

class User (
    val userId : Int
    ) {
    fun getCountUnreadChats() : Int {
        val chats = getChatList()
        var count = 0
        var userMessageList : List<Message>

        for (chat in chats) {
            userMessageList = chat.messageList.filter { (userId != it.authorId) and (!it.read) }
            if (userMessageList.size > 0) {
                count =+ 1
            }
        }
        return count
    }

    fun getChatList() : List<Chat> {
        val chatsList = getListChats()
        if (chatsList.size == 0) {
            throw NotFoundChatsException("No messages")
        }
        return chatsList
    }

    fun openChat(chatId : Int, messageId : Int, count : Int) : List<Message> {
        val chatsList = getChatList()
        val chat = chatsList.find { it.id == chatId }
        var messageList : MutableList<Message> = ArrayList()
        if (chat != null) {
            val fromIndex = chat.messageList.indexOfFirst { it.messageId == messageId }
            val toIndex = fromIndex + count
            messageList = chat.messageList.subList(fromIndex, toIndex)
            for ((index, message) in messageList.withIndex()) {
                if (message.authorId != userId) {
                    readMessage(chat, message)
                    messageList.set(index, message.copy(read = true))
                }
            }
        }
        return messageList
    }

    fun addMessageUser(anotherUserId : Int, text : String) : Int {
        return addMessage(userId, anotherUserId, text)
    }

    fun deleteMessageUser(message: Message) : Boolean {
        return deleteMessage(message)
    }

    fun deleteChatUser(chat : Chat) : Boolean {
        return deleteChat(chat)
    }

}