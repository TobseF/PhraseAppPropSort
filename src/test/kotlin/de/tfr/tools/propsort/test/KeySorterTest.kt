package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.paps.KeySorter

@DisplayName("KeySorter")
class KeySorterTest {

    @Test
    fun `Sort keys`() {
        val keys = mutableListOf("cc", "Ab", "cC", "a_", "_b", "Ba", "_a", "c_", "aA", "aa", "ab", "aB")

        keys.sortWith(KeySorter)
        val expected = listOf("Ab", "Ba", "_a", "_b", "aA", "aB", "a_", "aa", "ab", "cC", "c_", "cc")

        assertEquals(expected, keys)
    }
}