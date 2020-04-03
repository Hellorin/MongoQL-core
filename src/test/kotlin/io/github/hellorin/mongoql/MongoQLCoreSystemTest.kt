package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.graphql.GraphQLParams
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("system-tests")
internal class MongoQLCoreSystemTest {

    @Test
    fun `MongoDB localhost`() {
        val dbname = "myDatabase" // enter your database name
        val collectionName = "myCollection" // enter your collection name
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

    @Test
    fun `MongoDB atlas`() {
        val dbname = "myDatabase" // enter your database name
        val collectionName = "myCollection" // enter your collection name
        val rootName = "person"
        val (username, password) = Pair("user", "user") // enter your username/password
        val host = "mongoql-shard-00-01-sp4v4.mongodb.net" // enter your singlehostname
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

}