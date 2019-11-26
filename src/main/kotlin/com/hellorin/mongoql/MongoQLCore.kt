package com.hellorin.mongoql

import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.graphql.GraphQLParams
import mu.KotlinLogging
import java.lang.IllegalArgumentException
import java.util.*

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    if (args.size != 3) {
        throw IllegalArgumentException(
"""Must have exactly 3 arguments:
    - databaseName
    - collectionName
    - graphQLRootName""")
    }

    logger.info { "Welcome to MongQL-core. Let's generate our GraphQL model" }

    val dbname = args[0]
    val collectionName = args[1]
    val rootName = args[2]

    val stringRepresentationGraphQLSchema = MongoQLSchemaGenerator().generate(
            MongoDBParams.Builder(dbName = dbname, colName = collectionName).build(),
            GraphQLParams.Builder(rootName.capitalize()).build()
    ).joinToString(separator = "\n\n")

    logger.info { "GraphQL schema generation has been successful !" }
    logger.info { "Here it is: $stringRepresentationGraphQLSchema" }

    print(
            stringRepresentationGraphQLSchema
    )
}