object ChatService {
    private var chats = ArrayList<Chat>()
    private var messagesId = HashSet<Int>()

    fun addChat(thisUserId : Int, anotherUserId : Int) : Int {
        chats.add(Chat(chats.size + 1, thisUserId, anotherUserId))
        return chats.last().id
    }

    fun deleteChat(chat : Chat) : Boolean {
       return chats.remove(chat)
    }

    fun getListChats() : List<Chat> {
        return chats
    }

    fun addMessage(userId : Int, anotherUserId: Int, text : String) : Int {
        val chatId = chats.find{ ((it.firstUserId == userId) and (it.secondUserId == anotherUserId)) or
                ((it.secondUserId == userId) and (it.firstUserId == anotherUserId))}?.id
            ?: addChat(userId, anotherUserId)
        val index = chats.indexOfFirst { it.id == chatId }
        val messageId = messagesId.size + 1
        val message = Message(chatId, messageId, userId, anotherUserId, text)

        messagesId += messageId
        chats.get(index).messageList.add(message)

        return messageId
    }

    fun editMessage(messageId : Int, chatId: Int, userId : Int, editText : String) : Int {
        val indexChat = chats.indexOfFirst { it.id == chatId }
        val indexMessage = chats.get(indexChat).messageList.indexOfFirst { it.messageId == messageId }

        if (userId != chats.get(indexChat).messageList.get(indexMessage).authorId) {
            throw NoRightsException("No rights to download")
        }

        val message = chats.get(indexChat).messageList.get(indexMessage).copy(text = editText)
        chats.get(indexChat).messageList.set(indexMessage, message)
        return 1
    }

    fun deleteMessage(message: Message) : Boolean {
        val indexChat = chats.indexOfFirst { it.id == message.chatId }
        if (indexChat == -1) {
            return false
        }
        if (chats.get(indexChat).messageList.remove(message)) {
            if (chats.get(indexChat).messageList.size == 0) {
                deleteChat(chats.get(indexChat))
            }
            return true
        }
        return false
    }

    fun readMessage (chat: Chat, message: Message) {
        val indexChat = chats.indexOf(chat)
        val indexMessage = chats.get(indexChat).messageList.indexOf(message)
        chats.get(indexChat).messageList.set(indexMessage, message.copy(read = true))
    }

    fun clear() {
        chats.clear()
        messagesId.clear()
    }

    fun getChat (chatId : Int) : Chat? {
        return chats.find { it.id == chatId }
    }

    fun getMessage(chatId: Int, messageId: Int) : Message? {
        return chats.find { it.id == chatId }?.messageList?.find { it.messageId == messageId }
    }

}