package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

internal open class MongoVarietyShellExecutor {

    private fun prepareCommandParameters(mongoDBParams: MongoDBParams): MutableList<String> {
        val parameters = mutableListOf("mongo",
                mongoDBParams.dbName);
        if (mongoDBParams.username != null && mongoDBParams.password != null) {
            parameters.addAll(listOf(
                    "-u ${mongoDBParams.username}",
                    "-p ${mongoDBParams.password}"
            ));
        }

        parameters.addAll(listOf(
                "--quiet",
                "--eval",
                "\"var collection= '${mongoDBParams.colName}', outputFormat='json'\"",
                "variety.js")
        )

        return parameters
    }

    open fun execute(
            mongoDBParams: MongoDBParams,
            processStarter: ProcessStarter = ProcessStarter(),
            varietyScriptCloner: VarietyScriptCloner = VarietyScriptCloner()): Process {
        val parameters = prepareCommandParameters(mongoDBParams)

        // Ease execution in JAR
        val tempFolderName = System.getProperty("java.io.tmpdir")
        varietyScriptCloner.copyVarietyScriptToTmpDirectory(tempFolderName)

        // Call process
        return processStarter.startAndWaitFor(File(tempFolderName), parameters)
    }
}

open class VarietyScriptCloner {
    open fun copyVarietyScriptToTmpDirectory(filename: String){
        val tmpFile = File(System.getProperty("java.io.tmpdir") + File.separator + "variety.js")
        Files.copy(
                MongoVarietyShellExecutor::class.java.classLoader.getResourceAsStream("variety.js")!!,
                tmpFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        )
    }
}

open class ProcessStarter {
    open fun startAndWaitFor(directory: File, parameters: List<String>) : Process {
        val processBuilder = ProcessBuilder()
        processBuilder.command(parameters)
        processBuilder.directory(directory)

        val process = processBuilder.start()
        process.waitFor()

        return process
    }
}