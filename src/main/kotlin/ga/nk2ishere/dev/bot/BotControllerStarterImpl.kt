package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.annotations.BotController
import ga.nk2ishere.dev.bot.annotations.BotMethod
import ga.nk2ishere.dev.bot.annotations.BotMethodChecker
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotStarter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.HashMap


@Component
class BotControllerStarterImpl: BotStarter {
    private val checkerBaseClass = { msg: Message -> true }.javaClass
    val botControllerMap = HashMap<String, Triple<Any, Method, (Message) -> Boolean>>()
    override fun postProcessBeforeInitialization(bean: Any, name: String): Any? {
        if(bean.javaClass.isAnnotationPresent(BotController::class.java)) {
            val checkerList = bean.javaClass.declaredFields.filter { it.isAnnotationPresent(BotMethodChecker::class.java) }
            bean.javaClass.declaredMethods.filter { it.isAnnotationPresent(BotMethod::class.java) }.forEach { method ->
                val checker = checkerList.firstOrNull { it.getAnnotation(BotMethodChecker::class.java).id == method.getAnnotation(BotMethod::class.java).checker }
                checker?.isAccessible = true

                when {
                    method.parameterCount != 2 -> throw BotException("Method ${method.name} must have 2 parameters")
                    !method.parameters.any { it.type == Message::class.java  } -> throw BotException("Method ${method.name} must have parameter Message")
                    !method.parameters.any { it.type == Subscriber::class.java  } -> throw BotException("Method ${method.name} must have parameter Subscriber")
                    method.returnType != Message::class.java -> throw BotException("Method ${method.name} must return Message")
                    checker == null -> throw BotException("No checkers for ${method.name} in ${bean.javaClass.simpleName} found")
                    checker.get(bean).javaClass.genericInterfaces[0] != checkerBaseClass.genericInterfaces[0] -> throw BotException("Checker must be (Message) -> Boolean")
                        else -> botControllerMap[method.name] = Triple(bean, method, checker.get(bean) as (Message) -> Boolean)
                }
            }
        }
        return bean
    }
}