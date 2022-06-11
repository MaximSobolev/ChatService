import ChatService.addChat
import ChatService.addMessage
import ChatService.clear
import ChatService.deleteChat
import ChatService.deleteMessage
import ChatService.editMessage
import ChatService.getChat
import ChatService.getListChats
import ChatService.getMessage
import ChatService.readMessage
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChatServiceTest {
    @Before
    fun before() {
        clear()
    }

    @Test
    fun addChatTest() {
        val thisUserId = 1
        val anotherUserId = 2
        val result = addChat(thisUserId, anotherUserId)

        assertEquals(1 , result)
    }

    @Test
    fun deleteChatTest() {
        val thisUserId = 1
        val anotherUserId = 2
        val chatId = addChat(thisUserId, anotherUserId)
        val chat = getChat(chatId)
        var result = false
        if (chat != null) {
            result = deleteChat(chat)
        }

        assertTrue(result)
    }

    @Test
    fun getListChatsTest() {
        val thisUserId = 1
        val anotherUserId = 2
        addChat(thisUserId, anotherUserId)

        val chatList = getListChats()
        assertEquals(1, chatList.size)
    }

    @Test
    fun addMessageTest() {
        val userId = 1
        val anotherUserId = 2
        val text = "content"

        val result = addMessage(userId, anotherUserId, text)

        assertEquals(1, result)
    }

    @Test
    fun editMessageTest() {
        val thisUserId = 1
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(thisUserId, anotherUserId)
        val messageId = addMessage(thisUserId, anotherUserId, text)
        val editText = "Edit content"

        val result = editMessage(messageId, chatId, thisUserId, editText)

        assertEquals(1, result)
    }

    @Test(expected = NoRightsException :: class)
    fun editMessageWithException() {
        val thisUserId = 1
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(thisUserId, anotherUserId)
        val messageId = addMessage(anotherUserId, thisUserId, text)
        val editText = "Edit content"

        editMessage(messageId, chatId, thisUserId, editText)
    }

    @Test
    fun deleteMessageTest() {
        val thisUserId = 1
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(thisUserId, anotherUserId)
        val messageId = addMessage(anotherUserId, thisUserId, text)
        val message = getMessage(chatId, messageId)
        var result = false
        if (message != null) {
            result = deleteMessage(message)
        }

        assertTrue(result)
    }

    @Test
    fun deleteMessageNotFoundMessageTest() {
        val thisUserId = 1
        val anotherUserId = 2
        addChat(thisUserId, anotherUserId)
        val message = Message(2, 1, thisUserId, anotherUserId, "Content")

        val result = deleteMessage(message)
        assertFalse(result)
    }

    @Test
    fun readMessageTest() {
        val thisUserId = 1
        val anotherUserId = 2
        val text = "content"
        val chatId = addChat(thisUserId, anotherUserId)
        val chat = getChat(chatId)
        val messageId = addMessage(anotherUserId, thisUserId, text)
        val message = getMessage(chatId, messageId)

        if (chat != null) {
            if (message != null) {
                readMessage(chat, message)
            }
        }

        val editMessage = getMessage(chatId, messageId)
        if (editMessage != null) {
            assertTrue(editMessage.read)
        }
    }
}