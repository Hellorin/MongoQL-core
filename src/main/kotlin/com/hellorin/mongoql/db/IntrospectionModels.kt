package com.hellorin.mongoql.db

internal class IdJson(var key: String) {
    constructor(): this(key = "") // For JSON

    fun pathLength(): Int = key.split(".").size
}

internal class ValueJson(var types: Map<String, Int>) {
    constructor() : this(mapOf())
}

internal class ModelPathJson(var _id: IdJson, var value: ValueJson, var totalOccurrences: Int, var percentContaining: Double) {
    constructor(): this(IdJson(), ValueJson(), 0, 0.0)
}