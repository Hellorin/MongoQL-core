package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.graphql.GraphQLParams
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("system-tests")
internal class MongoQLCoreSystemTest {
    @Test
    fun mongoDbAtlasSystemTest() {
        val dbname = "myDatabase"
        val collectionName = "myCollection"
        val rootName = "person"
        val (username, password) = Pair("davidL", "davidL")
        val host = "cluster0-shard-00-02-ieyyb.mongodb.net"
        val port = 27017L
        val isUsingTLS = true
        val authenticationDatabase = "admin"
        val authenticationMechanism = "SCRAM-SHA-1"

        val stringRepresentationGraphQLSchema = MongoQLSchemaGenerator().generate(
                MongoDBParams.Builder(
                        dbName = dbname,
                        colName = collectionName,
                        username = username,
                        password = password,
                        host = host,
                        port = port,
                        isUsingTLS = isUsingTLS,
                        authenticationDatabase = authenticationDatabase,
                        authenticationMechanism = authenticationMechanism
                ).build(),
                GraphQLParams.Builder(rootName.capitalize()).build()
        ).joinToString(separator = "\n\n")

        print(
                stringRepresentationGraphQLSchema
        )
    }

    @Test
    fun mongoDbLocalSystemTest() {
        val dbname = "test"
        val collectionName = "coll"
        val rootName = "person"

        val stringRepresentationGraphQLSchema = MongoQLSchemaGenerator().generate(
                MongoDBParams.Builder(
                        dbName = dbname,
                        colName = collectionName
                ).build(),
                GraphQLParams.Builder(rootName.capitalize()).build()
        ).joinToString(separator = "\n\n")

        print(
                stringRepresentationGraphQLSchema
        )
    }
}