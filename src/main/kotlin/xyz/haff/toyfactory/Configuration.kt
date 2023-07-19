package xyz.haff.toyfactory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.expression.spel.standard.SpelExpression
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.messaging.MessageChannel

@Configuration
class Configuration(
    private val orderNormalizerService: OrderNormalizerService,
) {


    @Bean
    fun truckOrderChannel(): MessageChannel = DirectChannel()

    @Bean
    fun dollOrderChannel(): MessageChannel = DirectChannel()

    @Bean
    fun orderFlow(
        truckOrderChannel: MessageChannel,
        dollOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(
        { Order.generateRandom() },
        { poller { it.fixedDelay(1000).maxMessagesPerPoll(1) } }
    )
    {
        log("order")
        transform(orderNormalizerService, "normalize")
        log("normalized-order")
        split<Order> { it.lines }
        route<Order.Line> {
            when (it.type) {
                ToyType.TRUCK -> truckOrderChannel
                ToyType.DOLL -> dollOrderChannel
            }
        }
    }


    @Bean
    fun truckOrderFlow(
        truckOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(truckOrderChannel) {
        handle { println("TRUCK: Received order ${it.payload}") }
    }

    @Bean
    fun dollOrderFlow(
        dollOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(dollOrderChannel) {
        handle { println("DOLL: Received order ${it.payload}") }
    }

}