package xyz.haff.toyfactory

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow

@Configuration
class Configuration {


    @Bean
    fun messageSourceFlow(): StandardIntegrationFlow = integrationFlow(
        { Order.generateRandom() },
        { poller { it.fixedDelay(1000).maxMessagesPerPoll(1) } }
    )
    {
        handle { println(it.payload) }
    }


}