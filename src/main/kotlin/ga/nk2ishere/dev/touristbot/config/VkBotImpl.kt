package ga.nk2ishere.dev.touristbot.config

import com.github.debop.javatimes.toLocalDateTime
import com.petersamokhin.bots.sdk.clients.Group
import ga.nk2ishere.dev.bot.annotations.BotImpl
import ga.nk2ishere.dev.bot.data.Message
import ga.nk2ishere.dev.bot.data.MessageImage
import ga.nk2ishere.dev.bot.data.MessageText
import ga.nk2ishere.dev.bot.data.Subscriber
import ga.nk2ishere.dev.bot.interfaces.Bot
import ga.nk2ishere.dev.bot.interfaces.BotCommunicator
import ga.nk2ishere.dev.bot.interfaces.BotSubscriberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.io.File
import java.util.*
import com.petersamokhin.bots.sdk.objects.Message as VkMessage

@Configuration
@PropertySource("classpath:vk.properties")
@BotImpl
class VkBotImpl: Bot {
    @Autowired private lateinit var botCommunicator: BotCommunicator
    @Autowired private lateinit var botSubscriberRepository: BotSubscriberRepository
    lateinit var vkGroup: Group

    @Value("#{environment['vk.club']}") var clubId: Int = 0
    @Value("#{environment['vk.access_token']}") lateinit var accessToken: String
    override val botId: String
        get() = javaClass.simpleName

    override fun onStart() {
        vkGroup = Group(clubId, accessToken)
        vkGroup.onSimpleTextMessage {
            botCommunicator.onMessageReceived(
                    botSubscriberRepository.get(botId, it.from(vkGroup).authorId().toLong())
                            ?: Subscriber(botId, it.from(vkGroup).authorId().toLong(), it.from(vkGroup).title),
                    Message(Date(it.from(vkGroup).timestamp.toLong()))
                            .apply { this.addText(it.from(vkGroup).text) }
            )
        }
        vkGroup.onPhotoMessage {
            botCommunicator.onMessageReceived(
                    botSubscriberRepository.get(botId, it.from(vkGroup).authorId().toLong())
                            ?: Subscriber(botId, it.from(vkGroup).authorId().toLong(), it.from(vkGroup).title),
                    Message(Date(it.from(vkGroup).timestamp.toLong()))
                            .apply { this.addText(it.from(vkGroup).text) }
                            .apply { it.from(vkGroup).photos.forEach { println(it) } }
            )
        }
    }

    override fun sendText(subscriber: Subscriber, text: String) {
        VkMessage()
                .from(vkGroup)
                .to(subscriber.chatId.toInt())
                .text(text)
                .send()
    }

    override fun sendImage(subscriber: Subscriber, image: File) {
        VkMessage()
                .from(vkGroup)
                .to(subscriber.chatId.toInt())
                .photo(image.path)
                .send()
    }

    override fun sendMessage(subscriber: Subscriber, message: Message) {
        message.getItems().forEach {
            when (it) {
                is MessageText -> this.sendText(subscriber, it.text)
                is MessageImage -> this.sendImage(subscriber, it.image)
            }
        }
    }

}