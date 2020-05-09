package ga.nk2ishere.dev.bot.data

data class Subscriber(
        val botId: String,
        val chatId: Long,
        val userName: String,
        var state: SubscriberState? = null
) {
    abstract class SubscriberState
}