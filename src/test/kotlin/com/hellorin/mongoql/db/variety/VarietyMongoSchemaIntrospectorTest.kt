package com.hellorin.mongoql.db.variety

import com.hellorin.mongoql.db.ModelMapper
import com.hellorin.mongoql.db.ModelPathJson
import com.hellorin.mongoql.db.MongoDBParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*

class VarietyMongoSchemaIntrospectorTest {

    @Test
    fun `test variety introspector`() {
        // Given
        val params = MongoDBParams.Builder().build()

        val model = Mockito.mock(ModelPathJson::class.java)
        val mapper = object : ModelMapper<VarietyModelPathJson> {
            override fun map(models: List<VarietyModelPathJson>): List<ModelPathJson> = Collections.singletonList(model)
        }

        val shellExecutor = Mockito.mock(MongoVarietyShellExecutor::class.java)

        val process = Mockito.mock(Process::class.java)

        val byteInputStream = """
            [{"_id":{"key":""},"value":{"types":{}},"totalOccurrences":0,"percentContaining":0.0}]
            """.byteInputStream(Charsets.UTF_8)
        Mockito.`when`(process.inputStream).thenReturn(byteInputStream)

        Mockito.`when`(shellExecutor.execute(params)).thenReturn(process)

        // When
        val dbSchema = VarietyMongoSchemaIntrospector(mapper, shellExecutor).readAndParseMongoSchema(params)

        // Then
        assertThat(dbSchema).isNotEmpty

        val returnedModel = dbSchema.iterator().next()
        assertThat(returnedModel).isEqualTo(model)
    }
}