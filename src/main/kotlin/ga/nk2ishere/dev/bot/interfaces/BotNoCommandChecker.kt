package ga.nk2ishere.dev.bot.interfaces

import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber

interface BotNoCommandChecker {
    fun onNoCommand(subscriber: Subscriber, message: Message): Message
}