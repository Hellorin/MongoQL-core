package com.hellorin.mongoql

import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.graphql.GraphQLParams
import java.lang.IllegalArgumentException
import java.util.*

fun main(args: Array<String>) {
    if (args.size != 3) {
        throw IllegalArgumentException(
"""Must have exactly 3 arguments:
    - databaseName
    - collectionName
    - graphQLRootName""")
    }

    val dbname = args[0]
    val collectionName = args[1]
    val rootName = args[2]

    print(
        MongoQLSchemaGenerator().generate(
                MongoDBParams.Builder(dbName =  dbname, colName = collectionName).build(),
                GraphQLParams.Builder(rootName.capitalize()).build()
        ).joinToString(separator = "\n\n")
    )
}