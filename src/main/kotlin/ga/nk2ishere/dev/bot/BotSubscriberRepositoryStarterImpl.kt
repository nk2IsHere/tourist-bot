package ga.nk2ishere.dev.bot

import ga.nk2ishere.dev.bot.annotations.BotSubscriberRepositoryImpl
import ga.nk2ishere.dev.bot.interfaces.BotStarter
import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class BotSubscriberRepositoryStarterImpl: BotStarter {
    private val log = LoggerFactory.getLogger(javaClass)
    private var botSubscriberRepository: BotSubscriberRepository? = null
    @Bean() fun botSubscriberRepository() = botSubscriberRepository

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if(bean.javaClass.isAnnotationPresent(BotSubscriberRepositoryImpl::class.java))
            botSubscriberRepository = if(botSubscriberRepository == null) {
                when(bean) {
                    is BotSubscriberRepository -> bean
                    else -> throw BotException("Annotated BotSubscriberRepositoryImpl class ${bean.javaClass.simpleName} must implement BotSubscriberRepository")
                }
            } else {
                log.info("Only one class can be BotSubscriberRepositoryImpl, current: ${bean.javaClass.simpleName}, used: ${botSubscriberRepository?.javaClass?.simpleName}")
                botSubscriberRepository
            }
        return bean
    }

}