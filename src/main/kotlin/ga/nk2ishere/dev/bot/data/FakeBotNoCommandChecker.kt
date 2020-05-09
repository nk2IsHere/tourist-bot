package ga.nk2ishere.dev.bot.data

import ga.nk2ishere.dev.bot.interfaces.BotNoCommandChecker
import org.slf4j.LoggerFactory
import java.util.*

class FakeBotNoCommandChecker: BotNoCommandChecker {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun onNoCommand(subscriber: Subscriber, message: Message): Message {
        log.info("No command like $message found")
        return Message()
    }

}