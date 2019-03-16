package de.tfr.tools.propsort.test

import org.junit.Assert.assertEquals
import org.junit.Test
import tools.paps.KeySorter


class KeySorterTest {

    @Test
    fun sortKeysTest() {
        val keys = mutableListOf("cc", "Ab", "cC", "a_", "_b", "Ba", "_a", "c_", "aA", "aa", "ab", "aB")

        keys.sortWith(KeySorter)
        val expected = listOf("Ab", "Ba", "_a", "_b", "aA", "aB", "a_", "aa", "ab", "cC", "c_", "cc")

        assertEquals(expected, keys)
    }
}