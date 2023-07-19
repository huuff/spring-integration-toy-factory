package xyz.haff.toyfactory

import org.springframework.stereotype.Service

/**
 * Orders sometimes come un-normalized (the same item is repeated several times).
 * This service groups appearances of the same item so the order can be split more easily
 */
@Service
class OrderNormalizerService {

    fun normalize(order: Order): Order = Order(order.lines
        .groupBy { it.type }
        .map { (type, lines) -> Order.Line(type, lines.sumOf { it.amount }) }
    )

}