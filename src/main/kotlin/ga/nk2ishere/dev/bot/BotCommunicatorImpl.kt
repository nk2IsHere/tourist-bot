package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotCommunicator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnBean(BotSubscriberRepositoryStarterImpl::class, BotControllerStarterImpl::class)
class BotCommunicatorImpl: BotCommunicator {
    @Bean fun botCommunicator(): BotCommunicator = BotCommunicatorImpl()

    private val log = LoggerFactory.getLogger(this.javaClass)
    @Autowired private lateinit var botStarter: BotStarterImpl
    @Autowired private lateinit var botControllerStarter: BotControllerStarterImpl
    @Autowired private lateinit var botExceptionControllerStarter: BotExceptionControllerStarterImpl
    @Autowired private lateinit var botNoCommandCheckerStarter: BotNoCommandCheckerStarterImpl

    private fun handleException(commandName: String, exception: Exception, subscriber: Subscriber, message: Message) {
        log.error("Error ${exception.cause} while trying to invoke $commandName")
        botExceptionControllerStarter.botControllerMap.asSequence().firstOrNull { exception.cause!!.javaClass.toString() == it.key.toString() }?.let {
            log.error("Passing ${exception.cause} to ${it.value.first}")
            sendMessage(subscriber, it.value.second.invoke(it.value.first, subscriber, message) as Message)
        } ?: return
    }

    override fun onMessageReceived(subscriber: Subscriber, message: Message) {
        log.info("Got message from: $subscriber $message")
        botControllerStarter.botControllerMap.filter { it.value.third(message) }.also {
            if(it.isEmpty())
                sendMessage(subscriber, botNoCommandCheckerStarter.handleNoCommandMessage(subscriber, message))
        }.forEach {
            log.info("Passed message to ${it.key}")
            try {
                sendMessage(subscriber, it.value.second.invoke(it.value.first, subscriber, message) as Message)
            } catch (exception: Exception) {
                handleException(it.key, exception, subscriber, message)
            }
        }
    }
    override fun sendMessage(subscriber: Subscriber, message: Message) {
        botStarter.botImplMap.filter { subscriber.botId == it.value.botId }.forEach {
            it.value.sendMessage(subscriber, message)
        }
    }
}