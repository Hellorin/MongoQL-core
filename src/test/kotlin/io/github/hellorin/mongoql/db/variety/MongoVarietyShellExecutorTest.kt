package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.lang.IllegalArgumentException


internal class MongoVarietyShellExecutorTest {
    @Test
    fun `test with no parameters`() {
        // Given
        System.setProperty("os.name", "windows:")
        System.setProperty("java.io.tmpdir", "folder")

        val mongoDBParams = MongoDBParams.Builder("db", "col").build()

        val processStarter = getProcess(listOf("variety.cmd", "db/col", "--quiet", "--outputFormat=json"))

        // When
        MongoVarietyShellExecutor().execute(
                mongoDBParams,
                processStarter)
    }

    @Test
    fun `test with all parameters`() {
        // Given
        System.setProperty("os.name", "windows:")
        System.setProperty("java.io.tmpdir", "folder")

        val mongoDBParams = MongoDBParams.Builder("db", "col")
                .username(
                        "user"
                ).password(
                        "password"
                ).isUsingTLS(
                        true
                ).authenticationMechanism(
                        "SCRAM"
                ).authenticationDatabase(
                        "admin"
                ).host(
                        "localhost"
                ).build()

        val processStarter = getProcess(listOf(
                "variety.cmd",
                "db/col",
                "--quiet",
                "--outputFormat=json",
                "--host=localhost",
                "--ssl",
                "--authenticationDatabase=admin",
                "--authenticationMechanism=SCRAM",
                "--username=user",
                "--password=password")) //NOSONAR this isn't a hardcoded password

        // When/Then
        MongoVarietyShellExecutor().execute(
                mongoDBParams,
                processStarter)
    }

    private fun getProcess(expectedList : List<String>) : ProcessStarter {
        return object : ProcessStarter() {

            override fun startAndWaitFor(parameters: List<String>): Process {

                if (parameters != expectedList) throw IllegalArgumentException()

                return object : Process() {
                    override fun destroy() {
                        throw NotImplementedError() // Not needed
                    }

                    override fun exitValue(): Int {
                        throw NotImplementedError() // Not needed
                    }

                    override fun waitFor(): Int {
                        throw NotImplementedError() // Not needed
                    }

                    override fun getOutputStream(): OutputStream {
                        throw NotImplementedError() // Not needed
                    }

                    override fun getErrorStream(): InputStream {
                        throw NotImplementedError() // Not needed
                    }

                    override fun getInputStream(): InputStream {
                        throw NotImplementedError() // Not needed
                    }

                }
            }
        }
    }
}