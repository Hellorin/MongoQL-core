package io.github.hellorin.mongoql.db.variety

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class VarietyIdJsonTest {
    @Test
    fun `test empty constructor`() {
        // When
        val varietyIdJson = VarietyIdJson()

        // Then
        assertThat(varietyIdJson.key).isEqualTo("")
        assertThat(varietyIdJson.pathLength()).isEqualTo(1)
    }
}