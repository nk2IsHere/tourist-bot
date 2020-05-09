package ga.nk2ishere.dev.bot.data

import java.io.File
import java.util.*

sealed class MessageItem
data class MessageText(val text: String) : MessageItem()
data class MessageImage(val image: File) : MessageItem()

class Message(date: Date) {
    constructor(): this(Date(System.currentTimeMillis()))
    private val items = LinkedList<MessageItem>()

    fun addText(text: String) = items.add(MessageText(text))
    fun addPhoto(photo: File) = items.add(MessageImage(photo))

    fun getItems(): List<MessageItem> = items
}

fun Message.hasTextFull(text: String): Boolean = this.getItems()
        .filter { it is MessageText }
        .find { (it as MessageText).text == text } != null

fun Message.hasText(text: String): Boolean = this.getItems()
        .filter { it is MessageText }
        .find { (it as MessageText).text.contains(text) } != null

fun Message.matchesRegex(regex: Regex): Boolean = this.getItems()
        .filter { it is MessageText }
        .find { (it as MessageText).text.matches(regex) } != null

fun Message.containsRegex(regex: Regex): Boolean = this.getItems()
        .filter { it is MessageText }
        .find { (it as MessageText).text.contains(regex) } != null