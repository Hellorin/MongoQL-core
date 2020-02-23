package io.github.hellorin.mongoql.db

internal interface ModelMapper<T> {
    fun map(models: List<T>): List<ModelPathJson>
}