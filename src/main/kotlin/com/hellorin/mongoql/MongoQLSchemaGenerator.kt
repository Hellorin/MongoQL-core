package com.hellorin.mongoql

import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.db.MongoSchemaIntrospector
import com.hellorin.mongoql.db.variety.VarietyMongoSchemaIntrospector
import com.hellorin.mongoql.graphql.GraphQLParams
import com.hellorin.mongoql.graphql.GraphQLSchemaBuilder
import java.lang.UnsupportedOperationException

class MongoQLSchemaGenerator(
        private val mongoSchemaIntrospector: MongoSchemaIntrospector = VarietyMongoSchemaIntrospector(),
        private val schemaBuilder: GraphQLSchemaBuilder = GraphQLSchemaBuilder()) {

    fun generate(mongoDBParams: MongoDBParams, graphQLParams: GraphQLParams): List<Type> {
        val introspectedMongoSchema = mongoSchemaIntrospector.readAndParseMongoSchema(mongoDBParams)

        return schemaBuilder.build(graphQLParams, introspectedMongoSchema)
    }
}

internal object TypeMapper {
    fun adaptType(parentName: String, type: String) : String = when(type) {
        "ObjectId" -> "ID"
        "Number" -> "Int"
        "Object" -> parentName.capitalize()
        "Array" -> throw UnsupportedOperationException() // variety is not sufficient for arrays since it doesn't give sub types of the array
        else -> type
    }
}

class Attribute(val name: String,
                         val types: Set<String>,
                         val isNullable: Boolean) {
    override fun toString() = "\t$name : ${types.iterator().next()}${if (isNullable) "!" else ""}"
}

class Type(val typeName: String, val attributes: List<Attribute>) {
    override fun toString(): String {
        return """type ${typeName.capitalize()} {
${attributes.joinToString(separator = "\n")}
}"""
    }

    companion object {
        fun isNull(type: String) : Boolean = type == "null"
    }
}