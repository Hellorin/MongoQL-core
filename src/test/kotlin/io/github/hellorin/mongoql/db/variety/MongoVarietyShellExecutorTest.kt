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
        System.setProperty("java.io.tmpdir", "folder")

        val mongoDBParams = MongoDBParams.Builder("db", "col").build()

        val processStarter = object : ProcessStarter() {
            var isCorrect: Boolean = false

            override fun startAndWaitFor(directory: File, parameters: List<String>): Process {

                if (parameters == listOf("mongo", "db", "--quiet", "--eval", "\"var collection= 'col', outputFormat='json'\"", "variety.js")) {
                    isCorrect = true
                }

                return object : Process() {
                    override fun destroy() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun exitValue(): Int {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun waitFor(): Int {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getOutputStream(): OutputStream {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getErrorStream(): InputStream {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun getInputStream(): InputStream {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
            }
        }

        val varietyScriptCloner = object : VarietyScriptCloner() {
            override fun copyVarietyScriptToTmpDirectory(filename: String){
                // Do nothing
            }
        }

        // When
        val execute = MongoVarietyShellExecutor().execute(
                mongoDBParams,
                processStarter,
                varietyScriptCloner)

        // Then
        assertThat(processStarter.isCorrect).isEqualTo(true)
    }
}