package io.github.hellorin.mongoql.db

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IdJsonTest {

    @Test
    fun emptyConstructor() {
        // When
        val idJson = IdJson()

        // Then
        assertThat(idJson.key).isEqualTo("")
    }

    @Test
    fun pathLength() {
        // When
        val idJson = IdJson("a.given.path")

        // Then
        assertThat(idJson.pathLength()).isEqualTo(3)
    }
}