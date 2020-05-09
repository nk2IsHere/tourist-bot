package ga.nk2ishere.dev.touristbot.config

import ga.nk2ishere.dev.bot.annotations.BotSubscriberRepositoryImpl
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository
import org.springframework.stereotype.Component

@Component
@BotSubscriberRepositoryImpl
class BotRepositoryImpl: BotSubscriberRepository {
    val subscriberList = hashSetOf<Subscriber>()

    override fun get(botId: String, chatId: Long): Subscriber? = subscriberList.firstOrNull { it.chatId == chatId && it.botId == botId }
    override fun get(botId: String, userName: String): Subscriber? = subscriberList.firstOrNull { it.userName == userName &&  it.botId == botId }
    override fun contains(subscriber: Subscriber): Boolean = subscriberList.firstOrNull { it.chatId == subscriber.chatId } != null


    override fun put(subscriber: Subscriber) {
        if(subscriberList.firstOrNull { it.chatId == subscriber.chatId } == null)
            subscriberList.add(subscriber)
    }

    override fun edit(subscriber: Subscriber) {
        val oldSubscriber = subscriberList.firstOrNull { it.chatId == subscriber.chatId } ?: return
        subscriberList.remove(oldSubscriber)
        subscriberList.add(subscriber)
    }

    override fun remove(subscriber: Subscriber) {
        val oldSubscriber = subscriberList.firstOrNull { it.chatId == subscriber.chatId } ?: return
        subscriberList.remove(oldSubscriber)
    }

}