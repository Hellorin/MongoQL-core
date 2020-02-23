package io.github.hellorin.mongoql.db

internal abstract class MongoSchemaIntrospector {

    abstract fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson>
}