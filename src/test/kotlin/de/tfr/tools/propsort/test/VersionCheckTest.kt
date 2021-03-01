package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.paps.VersionCheck

@DisplayName("Version Check")
class VersionCheckTest {

    @Test
    fun `Version check for updates`() {
        val checkResult = VersionCheck.checkForUpdates()
        assertEquals("1.0.6", checkResult.currentVersion)
    }
}