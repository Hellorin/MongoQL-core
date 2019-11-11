package com.hellorin.mongoql.graphql

class GraphQLParams private constructor(val rootTypeName: String) {

    data class Builder(
            var rootTypeName: String = "root"
    )  {
        fun rootName(rootTypeName: String) = apply { this.rootTypeName = rootTypeName }
        fun build() = GraphQLParams(rootTypeName)
    }
}