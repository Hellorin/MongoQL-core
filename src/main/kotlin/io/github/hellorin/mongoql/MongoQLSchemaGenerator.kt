package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.db.MongoSchemaIntrospector
import io.github.hellorin.mongoql.db.variety.VarietyMongoSchemaIntrospector
import io.github.hellorin.mongoql.graphql.GraphQLParams
import io.github.hellorin.mongoql.graphql.GraphQLSchemaBuilder
import java.lang.UnsupportedOperationException

internal class MongoQLSchemaGenerator(
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

/**
 * A model class used to represent a GraphQL attribute
 *
 * @param name Name of the attribute
 * @param types List of types that this attribute can take
 * @param nullable Determine if this attribute can be null
 */
class Attribute(
        val name: String,
        val types: Set<String>,
        val nullable: Boolean) {
    /**
     * Return a textual representation of an attribute
     */
    override fun toString() = "\t$name : ${types.iterator().next()}${if (nullable) "" else "!"}"
}

/**
 * Class that represents a graphQL type
 *
 * @param typeName Name of the graphQL type
 * @param attributes List of attributes of this type
 */
class Type(val typeName: String, val attributes: List<Attribute>) {
    /**
     * Textual representation of a graphQL type
     *
     * @return a textual representation of a graphql type
     */
    override fun toString(): String {
        return """type ${typeName.capitalize()} {
${attributes.joinToString(separator = "\n")}
}"""
    }

    /**
     * Determine if this type is the main type of the graphql schema
     *
     * @return True if the type is the main graphql type, false otherwise
     */
    fun isMainType() = attributes.any { it.name == "_id" }

    /**
     * Static method of a graphql type (mostly helper
     */
    companion object {
        /**
         * Return if the type is defined as null
         *
         * @param type A given type in String
         *
         * @return True if the type given is null, false otherwise
         */
        fun isNull(type: String) = type == "null"
    }
}