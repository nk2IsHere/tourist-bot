package ga.nk2ishere.dev.bot.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotExceptionMethod(
        val value: KClass<*>
)