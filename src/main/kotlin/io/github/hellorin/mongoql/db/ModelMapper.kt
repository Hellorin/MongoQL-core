package io.github.hellorin.mongoql.db

interface ModelMapper<T> {
    fun map(models: List<T>): List<ModelPathJson>
}