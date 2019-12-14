package com.hellorin.mongoql.db.variety

import com.hellorin.mongoql.db.MongoDBParams
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class MongoVarietyShellExecutor {

    private fun prepareCommandParameters(mongoDBParams: MongoDBParams): List<String> {
        val parameters = mutableListOf("mongo",
                mongoDBParams.dbName);
        if (mongoDBParams.username != null && mongoDBParams.password != null) {
            parameters.addAll(listOf(
                    "-u ${mongoDBParams.username}",
                    "-p ${mongoDBParams.password}"
            ));
        }
        if (mongoDBParams.host != null) {
            parameters.add("--host ${mongoDBParams.host}")
        }
        parameters.addAll(listOf(
                "--quiet",
                "--eval",
                "\"var collection= '${mongoDBParams.colName}', outputFormat='json'\"",
                "variety.js")
        )

        return parameters
    }

    fun execute(mongoDBParams: MongoDBParams): Process {
        val processBuilder = ProcessBuilder()

        val parameters = prepareCommandParameters(mongoDBParams)

        // Prepare the command
        processBuilder.command(parameters)

        // Ease execution in JAR
        val tempFolderName = System.getProperty("java.io.tmpdir")
        copyVarietyScriptToTmpDirectory(tempFolderName)
        processBuilder.directory(File(tempFolderName))

        val process = processBuilder.start()
        process.waitFor()
        return process
    }

    private fun copyVarietyScriptToTmpDirectory(tempFolderName: String) {
        val tmpFile = File(System.getProperty("java.io.tmpdir") + File.separator + "variety.js")
        Files.copy(
                MongoVarietyShellExecutor::class.java.classLoader.getResourceAsStream("variety.js")!!,
                tmpFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        )
    }
}