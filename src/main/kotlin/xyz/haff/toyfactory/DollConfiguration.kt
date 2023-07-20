package xyz.haff.toyfactory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.messaging.MessageChannel

@Configuration
class DollConfiguration {

    @Bean
    fun dollOrderChannel(): MessageChannel = DirectChannel()

    @Bean
    fun dollOrderFlow(
        dollOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(dollOrderChannel) {
        log("doll")
        handle { }
    }
}