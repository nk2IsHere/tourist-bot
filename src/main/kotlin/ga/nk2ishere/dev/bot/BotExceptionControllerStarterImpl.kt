package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.annotations.*
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotStarter
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.HashMap
import kotlin.reflect.KClass

@Component
class BotExceptionControllerStarterImpl: BotStarter {
    val botControllerMap = HashMap<KClass<*>, Pair<Any, Method>>()
    override fun postProcessBeforeInitialization(bean: Any, name: String): Any? {
        if(bean.javaClass.isAnnotationPresent(BotExceptionController::class.java)) {
            bean.javaClass.declaredMethods.filter { it.isAnnotationPresent(BotExceptionMethod::class.java) }.forEach { method ->
                when {
                    method.parameterCount != 2 -> throw BotException("Method ${method.name} must have 2 parameters")
                    !method.parameters.any { it.type == Message::class.java  } -> throw BotException("Method ${method.name} must have parameter Message")
                    !method.parameters.any { it.type == Subscriber::class.java  } -> throw BotException("Method ${method.name} must have parameter Subscriber")
                    method.returnType != Message::class.java -> throw BotException("Method ${method.name} must return Message")
                    else -> botControllerMap[method.getAnnotation(BotExceptionMethod::class.java).value] = bean to method
                }
            }
        }
        return bean
    }
}