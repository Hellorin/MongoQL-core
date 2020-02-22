package io.github.hellorin.mongoql.graphql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GraphQLParamsTest {
    @Test
    fun `test graphql params builder`() {
        // When
        val graphQLParams = GraphQLParams.Builder("root").build()

        // Then
        assertThat(graphQLParams).isNotNull
        assertThat(graphQLParams.rootTypeName).isEqualTo("root")
    }
}