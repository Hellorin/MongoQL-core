package io.github.hellorin.mongoql.db.variety

import io.github.hellorin.mongoql.db.MongoDBParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MongoDBParamsTest {
    @Test
    fun `test mongo db params`() {
        // When
        var params = MongoDBParams.Builder(
                dbName = "db",
                colName = "col"
        ).host(
                "localhost"
        ).username(
                "user"
        ).password(
                "pwd"
        ).build()

        // Then
        assertThat(params).isNotNull
        assertThat(params.dbName).isEqualTo("db")
        assertThat(params.host).isEqualTo("localhost")
        assertThat(params.username).isEqualTo("user")
        assertThat(params.password).isEqualTo("pwd")
    }
}