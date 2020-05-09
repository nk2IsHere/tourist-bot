package ga.nk2ishere.dev.bot.interfaces

import ga.nk2ishere.dev.bot.data.Subscriber

interface BotSubscriberRepository {
    fun get(botId: String, chatId: Long): Subscriber?
    fun get(botId: String, userName: String): Subscriber?
    fun contains(subscriber: Subscriber): Boolean
    fun put(subscriber: Subscriber)
    fun edit(subscriber: Subscriber)
    fun remove(subscriber: Subscriber)
}