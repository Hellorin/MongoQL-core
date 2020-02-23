package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.graphql.GraphQLParams
import mu.KotlinLogging
import java.lang.IllegalArgumentException
import java.util.*

private val logger = KotlinLogging.logger {}

/**
 * Main used to test the generation of a GraphQL Schema from a MongoDB database/collection
 */
fun main(args: Array<String>) {
    if (args.size < 3) {
        throw IllegalArgumentException(
"""Must have at least 3 arguments:
    - databaseName
    - collectionName
    - graphQLRootName
    - [username:password]
    - [host]
    - """)
    }

    logger.info { "Welcome to MongoQL-core. Let's generate our GraphQL model" }

    val dbname = args[0]
    val collectionName = args[1]
    val rootName = args[2]
    val (username, password) = if (args.size > 4) Pair(args[3], args[4]) else Pair(null, null)
    val host = if (args.size > 5) args[5] else null

    val stringRepresentationGraphQLSchema = MongoQLSchemaGenerator().generate(
            MongoDBParams.Builder(
                    dbName = dbname,
                    colName = collectionName,
                    username = username,
                    password = password,
                    host = host
            ).build(),
            GraphQLParams.Builder(rootName.capitalize()).build()
    ).joinToString(separator = "\n\n")

    logger.info { "GraphQL schema generation has been successful !" }
    logger.info { "Here it is: $stringRepresentationGraphQLSchema" }

    print(
            stringRepresentationGraphQLSchema
    )
}