package com.hellorin.mongoql.db

public class MongoDBParams private constructor(
        val host: String?,
        val username: String?,
        val password: String?,
        val dbName: String?,
        val colName: String?) {

    data class Builder(
        var host: String? = null,
        var username: String? = null,
        var password: String? = null,
        var dbName: String? = "test",
        var colName: String? = "coll"
    ) {
        fun host(host: String) = apply { this.host = host}
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun dbName(dbName: String) = apply { this.dbName = dbName }
        fun colName(colName: String) = apply { this.colName = colName }

        fun build() = MongoDBParams(host, username, password, dbName, colName)
    }
}