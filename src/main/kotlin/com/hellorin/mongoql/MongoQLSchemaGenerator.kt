package com.hellorin.mongoql

import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.db.MongoSchemaIntrospector
import com.hellorin.mongoql.db.variety.VarietyMongoSchemaIntrospector
import com.hellorin.mongoql.graphql.GraphQLParams
import com.hellorin.mongoql.graphql.GraphQLSchemaBuilder

public class MongoQLSchemaGenerator(
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
        "Array" -> "[]" // TODO: variety is not sufficient for arrays since it doesn't give sub types of the array
        "Object" -> parentName.capitalize()
        else -> type
    }
}

class Attribute(private val name: String,
                         private val types: Set<String>,
                         private val isNullable: Boolean) {
    override fun toString() = "\t$name : ${types.iterator().next()}${if (isNullable) "!" else ""}"

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }

        if (other != null && other is Attribute) {
            return this.name == other.name
        }

        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

class Type(private val typeName: String, private val attributes: List<Attribute>) {
    override fun toString(): String {
        return """type ${typeName.capitalize()} {
${attributes.joinToString(separator = "\n")}
}"""
    }

    companion object {
        fun isNull(type: String) : Boolean = type == "null"
    }
}