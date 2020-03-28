package io.github.hellorin.mongoql.db.variety

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class ProcessStarterTest {
    @Test
    fun `start process starter`() {
        val processStarter = ProcessStarter()

        val folder = File(System.getProperty("java.io.tmpdir"))

        val osName = System.getProperty("os.name").toLowerCase()

        // TODO find a way to make it work with any platform
        if (osName.startsWith("windows")) {
            val process = processStarter.startAndWaitFor(folder, listOf("cmd.exe", "/c", "ping 8.8.8.8"))
            assertThat(process).isNotNull
            assertThat(process.outputStream).isNotNull
        }
    }
}