package io.github.hellorin.mongoql.db

/**
 * Class used to represent Mongo DB related parameters
 *
 * @param dbName MongoDB database name
 * @param colName MongoDB collection name
 * @param host MongoDB host
 * @param username username used to connect to the DB
 * @param password password used to connect to the DB
 */
class MongoDBParams private constructor(
        val dbName: String,
        val colName: String,
        val host: String?,
        val useURI: Boolean?,
        val clusterHost: String?,
        val port: Long?,
        val username: String?,
        val password: String?,
        val isUsingTLS: Boolean?,
        val authenticationDatabase: String?,
        val authenticationMechanism: String?
) {
    override fun toString() =
            """MongoDBParams[
            dbName: $dbName,
            colName: $colName,
            host: $host,
            useUri: $useURI,
            clusterHost: $clusterHost,
            port: $port,
            username: $username,
            password: ***,
            isUsingTLS: $isUsingTLS,
            authenticationDatabase: $authenticationDatabase,
            authenticationMechanism: $authenticationMechanism
        ]""".trimMargin()


    /**
     * Builder used to easily build MongoDB related parameters
     *
     * @param dbName MongoDB database name
     * @param colName MongoDB collection name
     * @param host MongoDB host
     * @param username username used to connect to the DB
     * @param password password used to connect to the DB
     */
    data class Builder(
            private var dbName: String,
            private var colName: String,
            private var host: String? = null,
            private var useURI: Boolean = false,
            private var clusterHost: String? = null,
            private var port: Long? = null,
            private var username: String? = null,
            private var password: String? = null,
            private var isUsingTLS: Boolean? = null,
            private var authenticationDatabase: String? = null,
            private var authenticationMechanism: String? = null
    ) {
        /**
         * Method used to change the hostname
         */
        fun host(host: String) = apply { this.host = host }

        /**
         * Method used to determine if we use URI connection's string or not
         */
        fun useURI(useURI: Boolean) = apply { this.useURI = useURI }

        /**
         * Method used to change the cluster hostname
         */
        fun clusterHost(clusterHost: String) = apply { this.clusterHost = clusterHost }

        /**
         * Method used to change the port
         */
        fun port(port: Long) = apply { this.port = port }

        /**
         * Method used to change the username
         */
        fun username(username: String) = apply { this.username = username }

        /**
         * Method used to change password
         */
        fun password(password: String) = apply { this.password = password }

        /**
         * Method used to change is we must use TLS
         */
        fun isUsingTLS(isUsingTLS: Boolean) = apply { this.isUsingTLS = isUsingTLS }

        /**
         * Method used to change the authentication database used
         */
        fun authenticationDatabase(authenticationDatabase: String) = apply { this.authenticationDatabase = authenticationDatabase }

        /**
         * Method used to change the authentication mechanism used
         */
        fun authenticationMechanism(authenticationMechanism: String) = apply { this.authenticationMechanism = authenticationMechanism }

        /**
         * Build a mongoDB parameters instance class
         *
         * @return A MongoDB parameters instance
         */
        fun build() = MongoDBParams(
                dbName,
                colName,
                host,
                useURI,
                clusterHost,
                port,
                username,
                password,
                isUsingTLS,
                authenticationDatabase,
                authenticationMechanism
        )
    }
}