package io.github.hellorin.mongoql.db.variety

/**
 * A generic variety exception
 *
 * @param varietyMessage The message received from the tool variety
 */
open class VarietyException(varietyMessage: String) : RuntimeException(varietyMessage)
