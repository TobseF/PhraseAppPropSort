package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.paps.sorter.KeySorter

@DisplayName("KeySorter")
class KeySorterTest {

    @Test
    fun `Sort list of keys`() {
        val keys = mutableListOf("cc", "Ab", "cC", "ccc", "a_", "_b", "Ba", "_a", "c_", "aA", "aa", "ab", "aB","Aa","0","1","00","01")

        keys.sortWith(KeySorter)
        val expected = listOf("0", "00", "01", "1", "Aa", "Ab", "Ba", "_a", "_b", "aA", "aB", "a_", "aa", "ab", "cC", "c_", "cc", "ccc")

        assertEquals(expected, keys)
    }
}