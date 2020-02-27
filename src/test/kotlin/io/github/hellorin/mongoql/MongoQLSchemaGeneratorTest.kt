package io.github.hellorin.mongoql

import io.github.hellorin.mongoql.db.ModelPathJson
import io.github.hellorin.mongoql.db.MongoDBParams
import io.github.hellorin.mongoql.db.MongoSchemaIntrospector
import io.github.hellorin.mongoql.db.variety.VarietyMongoSchemaIntrospector
import io.github.hellorin.mongoql.graphql.GraphQLParams
import io.github.hellorin.mongoql.graphql.GraphQLSchemaBuilder
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class MongoQLSchemaGeneratorTest {

    @Test
    fun `test schema generator `() {
        // Given
        val mongoSchemaIntrospectorMock = object : VarietyMongoSchemaIntrospector() {
            val model = ModelPathJson()
            override fun readAndParseMongoSchema(mongoDBParams: MongoDBParams): List<ModelPathJson> = Collections.singletonList(model)
        }

        val type = Type("type", emptyList())
        val schemaBuilderMock = object : GraphQLSchemaBuilder() {
            override fun build(graphQLParams: GraphQLParams, parsedMongoSchema: List<ModelPathJson>) = Collections.singletonList(type)
        }

        val mongoQLSchemaGenerator = MongoQLSchemaGenerator(mongoSchemaIntrospectorMock, schemaBuilderMock)

        val mongoDbParamsMock = MongoDBParams.Builder("test", "col").build()
        val graphqlParamsMock = GraphQLParams.Builder("root").build()

        // When
        val generatedSchema = mongoQLSchemaGenerator.generate(mongoDbParamsMock, graphqlParamsMock)

        // Then
        Assertions.assertThat(generatedSchema.size).isEqualTo(1)

        val typeReturned = generatedSchema.iterator().next()
        Assertions.assertThat(typeReturned).isEqualTo(type)
    }
}