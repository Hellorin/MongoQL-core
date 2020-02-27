package io.github.hellorin.mongoql.db

abstract class MongoSchemaIntrospector {

    abstract fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson>
}