package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.ModelMapper
import io.github.hellorin.mongoql.db.ModelPathJson
import io.github.hellorin.mongoql.db.MongoDBParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*

class VarietyMongoSchemaIntrospectorTest {

    @Test
    fun `test error in executor`() {
        // Given
        val params = MongoDBParams.Builder("db", "col").build()

        val model = ModelPathJson()
        val mapper = object : ModelMapper<VarietyModelPathJson> {
            override fun map(models: List<VarietyModelPathJson>): List<ModelPathJson> = Collections.singletonList(model)
        }

        val byteInputStream = """
            uncaught exception
            """.byteInputStream(Charsets.UTF_8)
        val process = object: Process() {
            override fun destroy() {
                // not required
            }

            // not required
            override fun exitValue(): Int = 0

            // not required
            override fun waitFor(): Int = 0

            // not required
            override fun getOutputStream(): OutputStream {
                throw NotImplementedError()
            }

            // not required
            override fun getErrorStream(): InputStream {
                throw NotImplementedError()
            }

            override fun getInputStream(): InputStream = byteInputStream

        }

        val shellExecutor = object : MongoVarietyShellExecutor() {
            override fun execute(
                    mongoDBParams: MongoDBParams,
                    processStarter: ProcessStarter) = process
        }

        // When
        Assertions.assertThrows(VarietyException::class.java) {
            VarietyMongoSchemaIntrospector(mapper, shellExecutor).readAndParseMongoSchema(params)
        }
    }

    @Test
    fun `test variety introspector`() {
        // Given
        val params = MongoDBParams.Builder("db", "col").build()

        val model = ModelPathJson()
        val mapper = object : ModelMapper<VarietyModelPathJson> {
            override fun map(models: List<VarietyModelPathJson>): List<ModelPathJson> = Collections.singletonList(model)
        }

        val byteInputStream = """
            [{"_id":{"key":""},"value":{"types":{}},"totalOccurrences":0,"percentContaining":0.0}]
            """.byteInputStream(Charsets.UTF_8)
        val process = object: Process() {
            override fun destroy() {
                // not required
            }

            // not required
            override fun exitValue(): Int = 0

            // not required
            override fun waitFor(): Int = 0

            // not required
            override fun getOutputStream(): OutputStream {
                throw NotImplementedError()
            }

            // not required
            override fun getErrorStream(): InputStream {
                throw NotImplementedError()
            }

            override fun getInputStream(): InputStream = byteInputStream

        }

        val shellExecutor = object : MongoVarietyShellExecutor() {
            override fun execute(
                    mongoDBParams: MongoDBParams,
                    processStarter: ProcessStarter) = process
        }

        // When
        val dbSchema = VarietyMongoSchemaIntrospector(mapper, shellExecutor).readAndParseMongoSchema(params)

        // Then
        assertThat(dbSchema).isNotEmpty

        val returnedModel = dbSchema.iterator().next()
        assertThat(returnedModel).isEqualTo(model)
    }
}