package com.hellorin.mongoql.db

import com.hellorin.mongoql.db.variety.VarietyModelPathJson

interface ModelMapper<T> {
    fun map(models: List<T>) : List<ModelPathJson>
}