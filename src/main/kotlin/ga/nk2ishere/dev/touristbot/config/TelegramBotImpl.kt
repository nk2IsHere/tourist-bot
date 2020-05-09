package ga.nk2ishere.dev.touristbot.config

import ga.nk2ishere.dev.bot.annotations.BotImpl
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.MessageImage
import ga.nk2ishere.dev.bot.data.MessageText
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.Bot
import ga.nk2ishere.dev.bot.interfaces.BotCommunicator
import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.methods.send.SendPhoto
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import java.io.File
import org.telegram.telegrambots.TelegramBotsApi
import java.util.*


@Configuration
@PropertySource("classpath:telegram.properties")
@BotImpl
class TelegramBotImpl: TelegramLongPollingBot(), Bot {
    @Autowired private lateinit var botCommunicator: BotCommunicator
    @Autowired private lateinit var telegramBotImpl: TelegramBotImpl
    @Autowired private lateinit var botSubscriberRepository: BotSubscriberRepository
    val log = LoggerFactory.getLogger(this.javaClass)

    @Value("#{environment['telegram.token']}") lateinit var token: String
    @Value("#{environment['telegram.username']}") lateinit var userName: String
    override val botId: String
        get() = javaClass.simpleName

    override fun getBotToken(): String = token
    override fun getBotUsername(): String = userName

    val telegramBotsApi = TelegramBotsApi()
    override fun onStart() {
        log.info("Starting TelegramBot $userName")
        telegramBotsApi.registerBot(telegramBotImpl)
    }

    override fun sendText(subscriber: Subscriber, text: String) {
        execute(SendMessage(subscriber.chatId, text))
    }

    override fun sendImage(subscriber: Subscriber, image: File) {
        sendPhoto(SendPhoto().setNewPhoto(image).setChatId(subscriber.chatId))
    }

    override fun sendMessage(subscriber: Subscriber, message: Message) {
        message.getItems().forEach {
            when (it) {
                is MessageText -> this.sendText(subscriber, it.text)
                is MessageImage -> this.sendImage(subscriber, it.image)
            }
        }
    }

    override fun onUpdateReceived(update: Update?) {
        update?.let {
            botCommunicator.onMessageReceived(
                    botSubscriberRepository.get(botId, update.message.chatId)
                            ?: Subscriber(botId, update.message.chatId, update.message.from.userName),
                    Message(Date(update.message.date.toLong()))
                            .apply { this.addText(it.message.text) }
            )
        }
    }
}