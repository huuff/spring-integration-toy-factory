package xyz.haff.toyfactory

enum class ToyType {
    TRUCK,
    DOLL,
    ;

    companion object {
        val VALUES by lazy { ToyType.values() }
    }

}