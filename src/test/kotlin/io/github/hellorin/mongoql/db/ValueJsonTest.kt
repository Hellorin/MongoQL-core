package io.github.hellorin.mongoql.db

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class ValueJsonTest {
    @Test
    fun emptyConstructor() {
        // When
        val valueJson = ValueJson()

        // Then
        assertThat(valueJson.types).isEqualTo(emptyMap<String, Int>())
    }
}