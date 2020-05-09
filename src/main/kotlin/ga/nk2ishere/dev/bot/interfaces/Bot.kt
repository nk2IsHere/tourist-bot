package ga.nk2ishere.dev.bot.interfaces

import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import java.io.File

interface Bot {
    val botId: String
    fun onStart()
    fun sendText(subscriber: Subscriber, text: String)
    fun sendImage(subscriber: Subscriber, image: File)
    fun sendMessage(subscriber: Subscriber, message: Message)
}
