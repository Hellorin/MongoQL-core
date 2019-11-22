package com.hellorin.mongoql

import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.graphql.GraphQLParams

fun main() {
    print(
        MongoQLSchemaGenerator().generate(
                MongoDBParams.Builder().build(),
                GraphQLParams.Builder().rootName("Person").build()
        )
    )
}