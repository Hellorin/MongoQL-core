package io.github.hellorin.mongoql.db.variety

open class VarietyException(private val varietyMessage: String) : RuntimeException(varietyMessage)

class VarietyDatabaseUnknown(private val dbname : String) : VarietyException("Database $dbname is unknown on the host specified")