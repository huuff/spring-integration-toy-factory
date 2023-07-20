package xyz.haff.toyfactory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.messaging.MessageChannel

@Configuration
class TruckConfiguration {

    @Bean
    fun truckOrderChannel(): MessageChannel = DirectChannel()

    @Bean
    fun truckOrderFlow(
        truckOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(truckOrderChannel) {
        log("truck")
        handle<ToyType> { _, headers ->
            println("Started making truck ${headers["correlationId"]}")
            Thread.sleep(1000)
            println("Finished making truck ${headers["correlationId"]}")
        }
    }
}