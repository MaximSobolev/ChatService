import ChatService.addChat
import ChatService.addMessage
import ChatService.clear
import ChatService.getChat
import ChatService.getMessage
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserTest {
    @Before
    fun before() {
        clear()
    }

    @Test
    fun getCountUnreadChatsTest() {
        val thisUserId = 1
        val user = User(thisUserId)
        val anotherUserId = 2
        val text = "content"
        addChat(thisUserId, anotherUserId)
        addMessage(anotherUserId, thisUserId, text)

        val result = user.getCountUnreadChats()

        assertEquals(1, result)
    }

    @Test
    fun getChatListTest() {
        val thisUserId = 1
        val user = User(thisUserId)
        val anotherUserId = 2
        val text = "content"
        addChat(thisUserId, anotherUserId)
        addMessage(anotherUserId, thisUserId, text)

        val result = user.getChatList()
        assertEquals(1, result.size)
    }

    @Test (expected = NotFoundChatsException :: class)
    fun getChatListWithException() {
        val thisUserId = 1
        val user = User(thisUserId)
        user.getChatList()
    }

    @Test
    fun openChatTest() {
        val thisUserId = 1
        val user = User(thisUserId)
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(thisUserId, anotherUserId)
        val messageId = addMessage(thisUserId, anotherUserId, text)
        addMessage(anotherUserId, thisUserId, text)

        val listMessage = user.openChat(chatId, messageId, 2)
        assertEquals(2, listMessage.size)
    }

    @Test
    fun addMessageUserTest() {
        val userId = 1
        val user = User(userId)
        val anotherUserId = 2
        val text = "content"

        val result = user.addMessageUser(anotherUserId, text)
        assertEquals(1, result)
    }

    @Test
    fun deleteMessageUserTest() {
        val userId = 1
        val user = User(userId)
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(user.userId, anotherUserId)
        val messageId = user.addMessageUser(anotherUserId, text)
        val message = getMessage(chatId, messageId)
        var result = false
        if (message != null) {
            result = user.deleteMessageUser(message)
        }
        assertTrue(result)
    }

    @Test
    fun deleteChatUserTest() {
        val userId = 1
        val user = User(userId)
        val anotherUserId = 2
        val chatId = addChat(user.userId, anotherUserId)
        val chat = getChat(chatId)
        var result = false

        if (chat != null) {
            result = user.deleteChatUser(chat)
        }

        assertTrue(result)
    }

}