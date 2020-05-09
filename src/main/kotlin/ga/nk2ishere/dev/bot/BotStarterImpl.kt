package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.annotations.BotImpl
import ga.nk2ishere.dev.bot.data.FakeBotSubscriberRepository
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.Bot
import ga.nk2ishere.dev.bot.interfaces.BotStarter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component
import java.util.HashMap
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses

@Component
class BotStarterImpl: BotStarter {
    val botImplMap = HashMap<String, Bot>()
    override fun postProcessBeforeInitialization(bean: Any, name: String): Any? {
        if(bean.javaClass.superclass.isAnnotationPresent(BotImpl::class.java))
            when(bean) {
                is Bot -> {
                    botImplMap[name] = bean
                    bean.onStart()
                }
                else -> throw BotException("Bean ${bean.javaClass.simpleName} cannot be used as bot!")
            }
        return bean
    }
}
