package xyz.haff.toyfactory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.messaging.MessageChannel

@Configuration
class Configuration() {


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
        { poller { it.fixedDelay(10000).maxMessagesPerPoll(1) } }
    )
    {
        log("order")
        split<Order> { order -> order.lines.flatMap { line -> List(line.amount) { line.type } } }
        route<ToyType> {
            when (it) {
                ToyType.TRUCK -> truckOrderChannel
                ToyType.DOLL -> dollOrderChannel
            }
        }
    }


    @Bean
    fun truckOrderFlow(
        truckOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(truckOrderChannel) {
        log()
        handle<ToyType> { _, headers ->
            println("Started making truck ${headers["correlationId"]}")
            Thread.sleep(1000)
            println("Finished making truck ${headers["correlationId"]}")
        }
    }

    @Bean
    fun dollOrderFlow(
        dollOrderChannel: MessageChannel,
    ): StandardIntegrationFlow = integrationFlow(dollOrderChannel) {
        log()
        handle { }
    }

}