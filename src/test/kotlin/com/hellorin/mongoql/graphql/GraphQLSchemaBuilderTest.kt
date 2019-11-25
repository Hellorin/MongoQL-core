package com.hellorin.mongoql.graphql

import com.hellorin.mongoql.db.IdJson
import com.hellorin.mongoql.db.ModelPathJson
import com.hellorin.mongoql.db.ValueJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.UnsupportedOperationException
import java.util.*

class GraphQLSchemaBuilderTest {
    @Test
    fun `test normal cases`() {
        // Given
        val graphQLParams = GraphQLParams.Builder("root").build()

        val modelPathJson0 = ModelPathJson(
                IdJson(key = "id"),
                ValueJson(types = mutableMapOf("ObjectId" to 1)),
                1,
                100.0
        )
        val modelPathJson1 = ModelPathJson(
                IdJson(key = "name"),
                ValueJson(types = mutableMapOf("String" to 1)),
                1,
                100.0
        )
        val modelPathJson2 = ModelPathJson(
                IdJson(key = "children"),
                ValueJson(types = mutableMapOf("Object" to 1)),
                1,
                100.0
        )
        val modelPathJson3 = ModelPathJson(
                IdJson(key = "children.name"),
                ValueJson(types = mutableMapOf("String" to 1, "null" to 1)),
                2,
                100.0
        )

        // When
        val list = GraphQLSchemaBuilder().build(
                graphQLParams,
                listOf(
                        modelPathJson0,
                        modelPathJson1,
                        modelPathJson2,
                        modelPathJson3
                )
        )

        // Then
        assertThat(list).isNotEmpty
        assertThat(list.size).isEqualTo(2)

        var root = list.find { type -> "root" == type.typeName }
        assertThat(root).isNotNull()
        root = root!!
        assertThat(root.typeName).isEqualTo("root")
        assertThat(root.attributes.size).isEqualTo(3)
        assertThat(root.attributes[0].name).isEqualTo("id")
        assertThat(root.attributes[0].types).isEqualTo(Collections.singleton("ID"))
        assertThat(root.attributes[0].isNullable).isEqualTo(false)
        assertThat(root.attributes[1].name).isEqualTo("name")
        assertThat(root.attributes[1].types).isEqualTo(Collections.singleton("String"))
        assertThat(root.attributes[1].isNullable).isEqualTo(false)
        assertThat(root.attributes[2].name).isEqualTo("children")
        assertThat(root.attributes[2].types).isEqualTo(Collections.singleton("Children"))
        assertThat(root.attributes[2].isNullable).isEqualTo(false)

        var children = list.find { type -> "children" == type.typeName }
        assertThat(children).isNotNull()
        children = children!!
        assertThat(children.typeName).isEqualTo("children")
        assertThat(children.attributes.size).isEqualTo(1)
        assertThat(children.attributes[0].name).isEqualTo("name")
        assertThat(children.attributes[0].types).isEqualTo(Collections.singleton("String"))
        assertThat(children.attributes[0].isNullable).isEqualTo(true)
    }

    @Test
    fun `test with unsupported array`() {
        // Given
        val graphQLParams = GraphQLParams.Builder("root").build()

        val modelPathJson0 = ModelPathJson(
                IdJson(key = "array"),
                ValueJson(types = mutableMapOf("Array" to 1)),
                1,
                100.0
        )

        // When
        Assertions.assertThrows(UnsupportedOperationException::class.java) {
            GraphQLSchemaBuilder().build(
                    graphQLParams,
                    listOf(
                            modelPathJson0
                    )
            )
        }
    }

    @Test
    fun `test with unsupported multi types`() {
        // Given
        val graphQLParams = GraphQLParams.Builder("root").build()

        val modelPathJson0 = ModelPathJson(
                IdJson(key = "array"),
                ValueJson(types = mutableMapOf("Int" to 1, "String" to 1)),
                2,
                100.0
        )

        // When
        Assertions.assertThrows(UnsupportedOperationException::class.java) {
            GraphQLSchemaBuilder().build(
                    graphQLParams,
                    listOf(
                            modelPathJson0
                    )
            )
        }
    }
}