package io.github.hellorin.mongoql.db

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ModelPathJsonTest {
    @Test
    fun testConstructor() {
        // When
        val modelPathJson = ModelPathJson()

        // Then
        assertThat(modelPathJson._id.key).isEqualTo("")
        assertThat(modelPathJson.percentContaining).isEqualTo(0.0)
        assertThat(modelPathJson.totalOccurrences).isEqualTo(0)
    }
}