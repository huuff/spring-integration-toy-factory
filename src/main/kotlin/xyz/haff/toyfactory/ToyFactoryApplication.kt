package xyz.haff.toyfactory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToyFactoryApplication

fun main(args: Array<String>) {
    runApplication<ToyFactoryApplication>(*args)
}
