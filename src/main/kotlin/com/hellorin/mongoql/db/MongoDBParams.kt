package com.hellorin.mongoql.db

public class MongoDBParams private constructor(
        val dbName: String,
        val colName: String,
        val host: String?,
        val username: String?,
        val password: String?
) {

    data class Builder(
            private var dbName: String,
            private var colName: String,
            private var host: String? = null,
            private var username: String? = null,
            private var password: String? = null
    ) {
        fun host(host: String) = apply { this.host = host }
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }

        fun build() = MongoDBParams(dbName, colName, host, username, password)
    }
}