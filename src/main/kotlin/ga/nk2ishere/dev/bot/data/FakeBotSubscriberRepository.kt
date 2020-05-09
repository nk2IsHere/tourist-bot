package ga.nk2ishere.dev.bot.data

import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository

class FakeBotSubscriberRepository: BotSubscriberRepository {
    override fun get(botId: String, chatId: Long): Subscriber = Subscriber(botId, chatId, "")
    override fun get(botId: String, userName: String): Subscriber = Subscriber(botId,0, userName)
    override fun contains(subscriber: Subscriber): Boolean = false
    override fun put(subscriber: Subscriber) {}
    override fun edit(subscriber: Subscriber) {}
    override fun remove(subscriber: Subscriber) {}
}