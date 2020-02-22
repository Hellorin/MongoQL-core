package io.github.hellorin.mongoql.db.variety

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*


class VarietyModelMapperTest {

    @Test
    fun `test model mapper for variety`() {
        // Given
        val modelMapper = VarietyModelMapper()

        val types = Collections.singletonMap("val", 1)
        val inputType = VarietyModelPathJson(
                VarietyIdJson("key"),
                VarietyValueJson(types),
                2,
                1.0
        )

        val inputData = Collections.singletonList<VarietyModelPathJson>(inputType)

        // When
        val mappedValues = modelMapper.map(inputData)

        // Then
        assertThat(mappedValues).isNotEmpty
        assertThat(mappedValues.size).isEqualTo(1)

        val value1 = mappedValues.iterator().next()
        assertThat(value1._id.key).isEqualTo("key")
        assertThat(value1.totalOccurrences).isEqualTo(2)
        assertThat(value1.percentContaining).isEqualTo(1.0)
    }
}