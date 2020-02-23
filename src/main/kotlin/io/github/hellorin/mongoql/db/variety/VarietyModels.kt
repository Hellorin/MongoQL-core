package io.github.hellorin.mongoql.db.variety

import com.fasterxml.jackson.databind.ObjectMapper

internal class VarietyIdJson(var key: String) {
    constructor(): this(key = "") // For JSON

    fun pathLength(): Int = key.split(".").size
}

internal class VarietyValueJson(var types: Map<String, Int>) {
    constructor() : this(mapOf())
}

internal class VarietyModelPathJson(var _id: VarietyIdJson, var value: VarietyValueJson, var totalOccurrences: Int, var percentContaining: Double) {
    constructor(): this(VarietyIdJson(), VarietyValueJson(), 0, 0.0)
}