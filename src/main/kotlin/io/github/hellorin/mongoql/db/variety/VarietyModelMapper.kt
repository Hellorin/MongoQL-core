package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.IdJson
import io.github.hellorin.mongoql.db.ModelMapper
import io.github.hellorin.mongoql.db.ModelPathJson
import io.github.hellorin.mongoql.db.ValueJson

internal class VarietyModelMapper : ModelMapper<VarietyModelPathJson> {
    override fun map(models: List<VarietyModelPathJson>) : List<ModelPathJson> = models.map { map(it) }

    private fun map(model: VarietyModelPathJson) : ModelPathJson = ModelPathJson(
            map(model._id),
            map(model.value),
            model.totalOccurrences,
            model.percentContaining
    )

    private fun map(model: VarietyIdJson): IdJson = IdJson(model.key)

    private fun map(model: VarietyValueJson): ValueJson = ValueJson(model.types)
}