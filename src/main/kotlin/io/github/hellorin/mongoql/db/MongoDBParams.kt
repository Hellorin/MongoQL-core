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
        val username: String?,
        val password: String?
) {

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
            private var username: String? = null,
            private var password: String? = null
    ) {
        /**
         * Method used to change the hostname
         */
        fun host(host: String) = apply { this.host = host }

        /**
         * Method used to change the username
         */
        fun username(username: String) = apply { this.username = username }

        /**
         * Method used to change password
         */
        fun password(password: String) = apply { this.password = password }

        /**
         * Build a mongoDB parameters instance class
         *
         * @return A MongoDB parameters instance
         */
        fun build() = MongoDBParams(dbName, colName, host, username, password)
    }
}