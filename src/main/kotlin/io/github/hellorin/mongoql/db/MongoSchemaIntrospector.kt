package io.github.hellorin.mongoql.db

public abstract class MongoSchemaIntrospector {

    abstract fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson>
}