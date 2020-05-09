package ga.nk2ishere.dev.bot.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotMethod(
        val checker: String
)