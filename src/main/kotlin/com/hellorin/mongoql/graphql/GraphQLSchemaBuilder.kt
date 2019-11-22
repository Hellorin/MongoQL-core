package com.hellorin.mongoql.graphql

import com.hellorin.mongoql.Attribute
import com.hellorin.mongoql.Type
import com.hellorin.mongoql.TypeMapper
import com.hellorin.mongoql.db.ModelPathJson

class GraphQLSchemaBuilder() {
    fun build(graphQLParams: GraphQLParams, parsedMongoSchema: List<ModelPathJson>) : List<Type> {
        // Index parsed object by path
        val indexedByPath = parsedMongoSchema.associateBy { it._id.key }

        val typesMap = mutableMapOf<String, MutableSet<Attribute>>()
        parsedMongoSchema.forEach {
            val split = it._id.key.split(".")
            val attributeName = split[split.size - 1]

            // Define the parent based on the key path
            val parent: String = if(split.size == 1) {
                graphQLParams.rootTypeName
            } else {
                split[split.size-2]
            }

            val attributesList = typesMap.getOrDefault(parent, mutableSetOf())

            val parentPath = split.take(split.size-1).joinToString( separator = ".")

            val attributeTypes = it.value.types.keys.map { type -> TypeMapper.adaptType(attributeName, type) }.toSet()
            val isNullable = !it.percentContaining.equals(indexedByPath[parentPath]?.percentContaining ?: 100.0)
                    || it.value.types.keys.any { type -> Type.isNull(type) }

            val attribute = Attribute(
                    attributeName,
                    attributeTypes,
                    isNullable
            )

            attributesList.add(attribute)
            typesMap[parent] = attributesList
        }

        return typesMap.entries.map { Type(it.key, it.value.toList()) }
    }
}