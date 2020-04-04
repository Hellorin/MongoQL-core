package io.github.hellorin.mongoql.db.variety

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.hellorin.mongoql.db.ModelMapper
import io.github.hellorin.mongoql.db.ModelPathJson
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.db.MongoSchemaIntrospector
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

internal open class VarietyMongoSchemaIntrospector(
        private val varietyModelMapper : ModelMapper<VarietyModelPathJson> = VarietyModelMapper(),
        private val mongoShellExecutor: MongoVarietyShellExecutor = MongoVarietyShellExecutor()) : MongoSchemaIntrospector() {
    private val objectMapper = ObjectMapper()

    private val logger = KotlinLogging.logger {}

    private fun introspectMongoSchema(mongoDBParams: MongoDBParams): String {
        logger.info { "Introspecting the MongoDB database/collection" }
        val process = mongoShellExecutor.execute(mongoDBParams)

        val processOutput: String = BufferedReader(InputStreamReader(process.inputStream, Charsets.UTF_8)).use { br ->
            br.lines().collect(Collectors.joining(System.lineSeparator()))
        }

        if ("uncaught exception".toRegex().containsMatchIn(processOutput)) {
            // Sanitize output
            var sanitizedProcessOutput = processOutput.replace("--password=${mongoDBParams.password}", "--password=***")

            logger.error { "Unexpected error while introspecting MongoDB database/collection. Details: $sanitizedProcessOutput" }
            throw VarietyException("Unexpected error while introspecting MongoDB database/collection")
        }

        return processOutput
    }

    override fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson> {
        val mockedData = introspectMongoSchema(mongoDBParams)

        return varietyModelMapper.map(objectMapper.readValue<List<VarietyModelPathJson>>(mockedData).sortedByDescending { it._id.pathLength() })
    }
}