package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import tools.paps.PhraseApi

/**
 * Tests which fire against the real phrase API endpoint.
 */
@Disabled
class ApiTest {

    object Config {
        val accessToken = "123"
        val projectID = "123"
    }

    @Test
    fun `Test find part of key - no Result`() {
        val api = PhraseApi(Config.accessToken)
        val keyId = api.findKeyId(Config.projectID, "TEST_VARARG")
        assertEquals(null, keyId)
    }

    @Test
    fun `Test find key`() {
        val api = PhraseApi(Config.accessToken)
        val keyId = api.findKeyId(Config.projectID, "delete_test_1")
        assertEquals("687ab29b07bb63a3adf349cfa147f909", keyId)
    }

    @Test
    fun `Test delete key`() {
        val api = PhraseApi(Config.accessToken)
        api.deleteKey(Config.projectID, "449d82234623a0d72d53cfcd25d0c113")
    }
}