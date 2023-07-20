package xyz.haff.toyfactory


/**
 * Orders sometimes come un-normalized (several lines for the same type of item) so they are joined
 * together into single, normalized order where there's only a single line for each type
 */
fun normalizeOrder(order: Order): Order = Order(
    order.lines
        .groupBy { it.type }
        .map { (type, lines) -> Order.Line(type, lines.sumOf { line -> line.amount }) }
)