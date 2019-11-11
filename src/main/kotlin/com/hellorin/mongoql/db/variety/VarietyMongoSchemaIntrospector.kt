package com.hellorin.mongoql.db.variety

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hellorin.mongoql.db.ModelPathJson
import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.db.MongoSchemaIntrospector
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.stream.Collectors

internal class VarietyMongoSchemaIntrospector : MongoSchemaIntrospector() {
    private val objectMapper = ObjectMapper()
    private val varietyModelMapper = VarietyModelMapper()

    private fun introspectMongoSchema(mongoDBParams: MongoDBParams): String {
        val processBuilder = ProcessBuilder()
        processBuilder.command(
                "mongo",
                mongoDBParams.dbName,
                "--quiet",
                "--eval",
                "\"var collection= '${mongoDBParams.colName}', outputFormat='json'\"",
                "variety.js")
        processBuilder.directory(File(this.javaClass.classLoader.getResource(".").file))

        val process = processBuilder.start()
        process.waitFor()

        return BufferedReader(InputStreamReader(process.inputStream, Charsets.UTF_8)).use { br ->
            return br.lines().collect(Collectors.joining(System.lineSeparator()))
        }
    }

    override fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson> {
        val mockedData = introspectMongoSchema(mongoDBParams)

        return varietyModelMapper.map(objectMapper.readValue<List<VarietyModelPathJson>>(mockedData).sortedByDescending { it._id.pathLength() })
    }
}