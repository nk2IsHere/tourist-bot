package ga.nk2ishere.dev.bot.interfaces

import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber

interface BotCommunicator {
    fun onMessageReceived(subscriber: Subscriber, message: Message)
    fun sendMessage(subscriber: Subscriber, message: Message)
}