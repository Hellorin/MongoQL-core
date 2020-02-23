package io.github.hellorin.mongoql.db.variety

/**
 * A generic variety exception
 *
 * @param varietyMessage The message received from the tool variety
 */
open class VarietyException(varietyMessage: String) : RuntimeException(varietyMessage)

/**
 * An exception to express that the database cannot be found. Not used currently
 *
 * @param dbname The database's name
 *
 */
class VarietyDatabaseUnknown(dbname : String) : VarietyException("Database $dbname is unknown on the host specified")