package com.hellorin.mongoql.db

public class IdJson(var key: String) {
    constructor(): this(key = "") // For JSON

    fun pathLength(): Int = key.split(".").size
}

public class ValueJson(var types: Map<String, Int>) {
    constructor() : this(mapOf())
}

public class ModelPathJson(var _id: IdJson, var value: ValueJson, var totalOccurrences: Int, var percentContaining: Double) {
    constructor(): this(IdJson(), ValueJson(), 0, 0.0)
}