package ga.nk2ishere.dev.touristbot.bot

import ga.nk2ishere.dev.bot.annotations.BotNoCommandCheckerImpl
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.BotNoCommandChecker
import org.springframework.stereotype.Component

@Component
@BotNoCommandCheckerImpl
class NoCommandController: BotNoCommandChecker {
    override fun onNoCommand(subscriber: Subscriber, message: Message): Message =
            Message().apply { addText("Oh! There's no command found like that") }
}