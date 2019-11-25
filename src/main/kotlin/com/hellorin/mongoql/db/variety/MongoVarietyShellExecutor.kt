package com.hellorin.mongoql.db.variety

import com.hellorin.mongoql.db.MongoDBParams
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class MongoVarietyShellExecutor {

    fun execute(mongoDBParams: MongoDBParams) : Process {
        val processBuilder = ProcessBuilder()
        processBuilder.command(
                "mongo",
                mongoDBParams.dbName,
                "--quiet",
                "--eval",
                "\"var collection= '${mongoDBParams.colName}', outputFormat='json'\"",
                "variety.js")

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