package xyz.haff.toyfactory


data class Order(val lines: Iterable<Line>) {
    data class Line(val type: ToyType, val amount: Int) {
        companion object {
            fun generateRandom() = Line(type = ToyType.VALUES.random(), amount = (1..5).random())
        }
    }

    companion object {
        fun generateRandom() = Order(List((1..2).random()) { Line.generateRandom() })
    }

}
