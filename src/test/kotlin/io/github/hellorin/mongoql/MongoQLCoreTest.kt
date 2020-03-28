package io.github.hellorin.mongoql

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class MongoQLCoreTest {
    @Test
    fun `main with less than 3 arguments`() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            main(arrayOf("1 parameters"))
        }
    }
}