package io.github.hellorin.mongoql.graphql

/**
 * Class used to represent the various GraphQL input parameters
 *
 * @param rootTypeName The root type name of the GraphQL schema
 */
class GraphQLParams private constructor(val rootTypeName: String) {

    /**
     * Builder used to ease building for GraphQL parameters
     *
     * @param rootTypeName The root type name of the GraphQL schema
     */
    data class Builder(
            private var rootTypeName: String
    )  {
        /**
         * Build a GraphQL input parameters instance
         *
         * @return A GraphQL input parameter instance
         */
        fun build() = GraphQLParams(rootTypeName)
    }
}