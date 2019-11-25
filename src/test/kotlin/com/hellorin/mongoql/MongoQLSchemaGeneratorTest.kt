package com.hellorin.mongoql

import com.hellorin.mongoql.db.ModelPathJson
import com.hellorin.mongoql.db.MongoDBParams
import com.hellorin.mongoql.db.MongoSchemaIntrospector
import com.hellorin.mongoql.db.variety.VarietyMongoSchemaIntrospector
import com.hellorin.mongoql.graphql.GraphQLParams
import com.hellorin.mongoql.graphql.GraphQLSchemaBuilder
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class MongoQLSchemaGeneratorTest {

    @Test
    fun `test schema generator `() {
        // Given
        val mongoSchemaIntrospectorMock = Mockito.mock(VarietyMongoSchemaIntrospector::class.java)
        val schemaBuilderMock = Mockito.mock(GraphQLSchemaBuilder::class.java)
        val mongoQLSchemaGenerator = MongoQLSchemaGenerator(mongoSchemaIntrospectorMock, schemaBuilderMock)

        val mongoDbParamsMock = MongoDBParams.Builder("test", "col").build()
        val graphqlParamsMock = GraphQLParams.Builder("root").build()

        val model = Mockito.mock(ModelPathJson::class.java)
        Mockito.`when`(mongoSchemaIntrospectorMock.readAndParseMongoSchema(mongoDbParamsMock)).thenReturn(Collections.singletonList(model))

        val type = Mockito.mock(Type::class.java)
        Mockito.`when`(schemaBuilderMock.build(graphqlParamsMock, Collections.singletonList(model))).thenReturn(Collections.singletonList(type))

        // When
        val generatedSchema = mongoQLSchemaGenerator.generate(mongoDbParamsMock, graphqlParamsMock)

        // Then
        Assertions.assertThat(generatedSchema.size).isEqualTo(1)

        val typeReturned = generatedSchema.iterator().next()
        Assertions.assertThat(typeReturned).isEqualTo(type)
    }
}