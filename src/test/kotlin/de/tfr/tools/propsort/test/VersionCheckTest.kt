package de.tfr.tools.propsort.test

import org.junit.Assert.assertEquals
import org.junit.Test
import tools.paps.VersionCheck

class VersionCheckTest {

    @Test
    fun versionCheckTest() {
        val checkResult = VersionCheck.checkForUpdates()
        assertEquals("1.0.5", checkResult.currentVersion)
    }
}