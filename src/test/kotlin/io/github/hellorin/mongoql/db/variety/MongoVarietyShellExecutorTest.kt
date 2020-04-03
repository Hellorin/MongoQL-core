package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import java.io.File
import java.io.InputStream
import java.io.OutputStream

internal class MongoVarietyShellExecutorTest {
    @Test
    fun `test`() {
        // Given
        System.setProperty("os.name", "windows:")
        System.setProperty("java.io.tmpdir", "folder")

        val mongoDBParams = MongoDBParams.Builder("db", "col").build()

        val processStarter = object : ProcessStarter() {
            var isCorrect: Boolean = false

            override fun startAndWaitFor(parameters: List<String>): Process {

                if (parameters == listOf("variety.cmd", "db/col", "--quiet", "--outputFormat=json")) {
                    isCorrect = true
                }

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

        // When
        MongoVarietyShellExecutor().execute(
                mongoDBParams,
                processStarter)

        // Then
        assertThat(processStarter.isCorrect).isEqualTo(true)
    }
}