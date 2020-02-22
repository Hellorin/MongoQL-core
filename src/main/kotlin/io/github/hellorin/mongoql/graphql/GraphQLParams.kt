package io.github.hellorin.mongoql.graphql

class GraphQLParams private constructor(val rootTypeName: String) {

    data class Builder(
            private var rootTypeName: String
    )  {
        fun build() = GraphQLParams(rootTypeName)
    }
}