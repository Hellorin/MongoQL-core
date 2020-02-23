package io.github.hellorin.mongoql.db

internal class IdJson(var key: String) {
    /**
     * Only for Jackson deserialization uses
     */
    internal constructor(): this(key = "") // For JSON

    fun pathLength(): Int = key.split(".").size
}

internal class ValueJson(var types: Map<String, Int>) {
    /**
     * Only for Jackson deserialization uses
     */
    internal constructor() : this(mapOf())
}

/**
 * @param _id
 * @param value
 * @param totalOccurrences
 * @param percentContaining
 */
internal class ModelPathJson(var _id: IdJson, var value: ValueJson, var totalOccurrences: Int, var percentContaining: Double) {
    /**
     * Only for Jackson deserialization uses
     */
    constructor(): this(IdJson(), ValueJson(), 0, 0.0)
}