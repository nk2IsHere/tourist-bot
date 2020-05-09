package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.annotations.BotNoCommandCheckerImpl
import ga.nk2ishere.dev.bot.annotations.BotSubscriberRepositoryImpl
import ga.nk2ishere.dev.bot.data.FakeBotNoCommandChecker
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotNoCommandChecker
import ga.nk2ishere.dev.bot.interfaces.BotStarter
import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class BotNoCommandCheckerStarterImpl: BotStarter {
    private val defaultBotNoCommandChecker = FakeBotNoCommandChecker()
    private var botNoCommandChecker: BotNoCommandChecker? = null
    fun handleNoCommandMessage(subscriber: Subscriber, message: Message) = botNoCommandChecker?.onNoCommand(subscriber, message)
            ?: defaultBotNoCommandChecker.onNoCommand(subscriber, message)

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if(bean.javaClass.isAnnotationPresent(BotNoCommandCheckerImpl::class.java))
            if(botNoCommandChecker == null) {
                when(bean) {
                    is BotNoCommandChecker -> botNoCommandChecker = bean
                    else -> throw BotException("Annotated BotNoCommandCheckerImpl class ${bean.javaClass.simpleName} must implement BotNoCommandChecker")
                }
            } else throw BotException("Only one class can be BotNoCommandCheckerImpl, current: ${bean.javaClass.simpleName}, used: ${botNoCommandChecker?.javaClass?.simpleName}")
        return bean
    }

}