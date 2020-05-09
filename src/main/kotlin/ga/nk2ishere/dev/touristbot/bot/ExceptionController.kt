package ga.nk2ishere.dev.touristbot.bot

import ga.nk2ishere.dev.bot.annotations.BotExceptionController
import ga.nk2ishere.dev.bot.annotations.BotExceptionMethod
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import org.springframework.stereotype.Component

@Component
@BotExceptionController
class ExceptionController {
    @BotExceptionMethod(NullPointerException::class)
    fun handleNullPointerException(subscriber: Subscriber, message: Message) =
            Message().apply { addText("Uh oh! Something bad happened") }
}