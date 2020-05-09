package ga.nk2ishere.dev.touristbot.bot

import ga.nk2ishere.dev.bot.annotations.BotController
import ga.nk2ishere.dev.bot.annotations.BotMethod
import ga.nk2ishere.dev.bot.annotations.BotMethodChecker
import ga.nk2ishere.dev.bot.data.*
import org.springframework.stereotype.Component

@Component
@BotController
class StartController {
    @BotMethodChecker("/hi") @JvmField final val checkHiCommand = { msg: Message -> msg.hasTextFull("/hi") }
    @BotMethod("/hi") fun hiCommand(subscriber: Subscriber, message: Message): Message {
        return Message().apply { this.addText("Welcome!") }
    }
}