package com.hellorin.mongoql.db.variety

import com.hellorin.mongoql.db.MongoDBParams
import java.io.File

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
        processBuilder.directory(File(this.javaClass.classLoader.getResource(".").file))

        val process = processBuilder.start()
        process.waitFor()
        return process
    }
}