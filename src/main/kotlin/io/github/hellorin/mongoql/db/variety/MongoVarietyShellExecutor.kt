package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams
import mu.KotlinLogging
import java.util.*


internal open class MongoVarietyShellExecutor {
    private val logger = KotlinLogging.logger {}

    private fun determineWhichVarietyToTake(): String {
        val os = System.getProperty("os.name").toLowerCase()

        return if (os.contains("windows")) "variety.cmd" else "variety"
    }

    private fun prepareCommandParameters(mongoDBParams: MongoDBParams): MutableList<String> {
        val parameters = mutableListOf(
                determineWhichVarietyToTake(),
                "${mongoDBParams.dbName}/${mongoDBParams.colName}",
                "--quiet",
                "--outputFormat=json")

        mongoDBParams.host?.let { parameters.add("--host=$it") }
        mongoDBParams.port?.let { parameters.add("--port=$it") }
        mongoDBParams.isUsingTLS?.let { if (it) parameters.add("--ssl") }
        mongoDBParams.authenticationDatabase?.let { parameters.add("--authenticationDatabase=$it") }
        mongoDBParams.authenticationMechanism?.let { parameters.add("--authenticationMechanism=$it") }

        if (mongoDBParams.username != null && mongoDBParams.password != null) {
            parameters.addAll(listOf(
                    "--username=${mongoDBParams.username}",
                    "--password=${mongoDBParams.password}"
            ));
        }

        return parameters
    }

    open fun execute(
            mongoDBParams: MongoDBParams,
            processStarter: ProcessStarter = ProcessStarter()): Process {
        val parameters = prepareCommandParameters(mongoDBParams)

        // Log for debug the parameters (just to be sure they are correctly entered)
        val filteredParameters = parameters.filter {
            !it.contains("--password")
        }
        logger.info { "Parameters are : $filteredParameters" }

        // Call process
        return processStarter.startAndWaitFor(parameters)
    }
}

open class ProcessStarter {
    open fun startAndWaitFor(parameters: List<String>): Process {
        val envProps = mapToStringArray(HashMap(System.getenv()))

        val process = Runtime.getRuntime().exec(parameters.joinToString(" "), envProps)
        process.waitFor()

        return process
    }

    open fun mapToStringArray(map: MutableMap<String, String>): Array<String?> {
        val strings = arrayOfNulls<String>(map.size)
        var i = 0
        for ((key, value) in map) {
            strings[i] = "$key=$value"
            i++
        }
        return strings
    }
}