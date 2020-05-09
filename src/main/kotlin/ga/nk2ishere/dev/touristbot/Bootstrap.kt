package ga.nk2ishere.dev.touristbot

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication(scanBasePackages = [
    "ga.nk2ishere.dev.touristbot",
    "ga.nk2ishere.dev.bot"
])
@EnableAutoConfiguration(exclude = [JacksonAutoConfiguration::class])
class Bootstrap: CommandLineRunner {
    override fun run(vararg args: String?) {

    }
}

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    runApplication<Bootstrap>(*args)
}
