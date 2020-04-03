package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams

internal open class MongoVarietyShellExecutor {

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
        mongoDBParams.isUsingTLS?.let { if (it) parameters.add("--tls") }
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

        // Call process
        return processStarter.startAndWaitFor(parameters)
    }
}

open class ProcessStarter {
    open fun startAndWaitFor(parameters: List<String>): Process {
        val processBuilder = ProcessBuilder()
        processBuilder.command(parameters)

        val process = processBuilder.start()
        process.waitFor()

        return process
    }
}