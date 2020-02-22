package io.github.hellorin.mongoql.graphql

import io.github.hellorin.mongoql.Attribute
import io.github.hellorin.mongoql.Type
import io.github.hellorin.mongoql.TypeMapper
import io.github.hellorin.mongoql.db.ModelPathJson
import mu.KotlinLogging
import java.lang.UnsupportedOperationException

class GraphQLSchemaBuilder() {
    private val logger = KotlinLogging.logger {}

    fun build(graphQLParams: GraphQLParams, parsedMongoSchema: List<ModelPathJson>) : List<Type> {
        logger.info { "Building our type from the introspected models" }

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

            val attributeTypes = it.value.types.keys.filter { type -> "null" != type }.map { type -> TypeMapper.adaptType(attributeName, type) }.toSet()
            if (attributeTypes.size > 1) {
                // Currently unsupported
                throw UnsupportedOperationException()
            }

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

        val mappedTypes = typesMap.entries.map { Type(it.key, it.value.toList()) }

        logger.info { "GraphQL model is ready to be printed" }

        return mappedTypes
    }
}