package io.github.hellorin.mongoql.db.variety

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("integration-tests")
internal class ProcessStarterTest {
    @Test
    fun `start process starter`() {
        val processStarter = ProcessStarter()

        val osName = System.getProperty("os.name").toLowerCase()
        if (osName.startsWith("windows")) {
            val process = processStarter.startAndWaitFor(listOf("cmd.exe", "/c", "ping 8.8.8.8"))
            assertThat(process).isNotNull
            assertThat(process.outputStream).isNotNull
        } else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            val process = processStarter.startAndWaitFor(listOf("false"))
            assertThat(process).isNotNull
            assertThat(process.outputStream).isNotNull
        }
    }
}