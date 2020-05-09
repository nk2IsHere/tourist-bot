package ga.nk2ishere.dev.bot.annotations

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotMethodChecker(
        val id: String
)