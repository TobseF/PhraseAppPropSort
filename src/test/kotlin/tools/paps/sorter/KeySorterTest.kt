package tools.paps.sorter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Test for [KeySorter]
 */
internal class KeySorterTest{

    @Test
    fun `compare char digits`() {
        assertEquals( KeySorter.compare("0", "0"),0)
        assertEquals( KeySorter.compare("1", "1"),0)

        assertEquals( KeySorter.compare("1", "2"),-1)

        assertEquals( KeySorter.compare("1", "a"),1)
        assertEquals( KeySorter.compare("1", "A"),1)
    }
    @Test
    fun `compare digits`() {
        assertEquals(KeySorter.compare("00", "00"), 0)
        assertEquals(KeySorter.compare("01", "01"), 0)

        assertEquals(KeySorter.compare("01", "02"), -1)

        assertEquals(KeySorter.compare("02", "01"), 1)
    }

    @Test
    fun `compare length`() {
        assertEquals(KeySorter.compare("0", "00"), -1)
        assertEquals(KeySorter.compare("", ""), 0)
        assertEquals(KeySorter.compare("0", ""), 1)
        assertEquals(KeySorter.compare("00", "0"), 1)

        assertEquals(KeySorter.compare("000", "00"), 1)
        assertEquals(KeySorter.compare("a", "aa"), -1)
        assertEquals(KeySorter.compare("", "0"), -1)
    }

    @Test
    fun `compare char uppercase letters`() {
        assertEquals( KeySorter.compare("A", "A"),0)
        assertEquals( KeySorter.compare("A", "B"),-1)
        assertEquals( KeySorter.compare("B", "A"),1)

        assertEquals( KeySorter.compare("B", "a"),1)
        assertEquals( KeySorter.compare("B", "b"),1)
        assertEquals( KeySorter.compare("C", "b"),1)

        assertEquals( KeySorter.compare("A", "0"),-1)
        assertEquals( KeySorter.compare("A", "1"),-1)
    }

    @Test
    fun `compare char lowercase letters`() {
        assertEquals( KeySorter.compare("a", "a"),0)
        assertEquals( KeySorter.compare("a", "b"),-1)
        assertEquals( KeySorter.compare("b", "a"),1)

        assertEquals( KeySorter.compare("a", "A"),-1)
        assertEquals( KeySorter.compare("a", "B"),-1)
        assertEquals( KeySorter.compare("b", "A"),-1)

        assertEquals( KeySorter.compare("b", "A"),-1)
        assertEquals( KeySorter.compare("b", "B"),-1)
        assertEquals( KeySorter.compare("c", "B"),-1)

        assertEquals( KeySorter.compare("a", "0"),-1)
        assertEquals( KeySorter.compare("a", "1"),-1)
    }

    @Test
    fun `compare char underscore`() {
        assertEquals( KeySorter.compare("_", "0"),-1)
        assertEquals( KeySorter.compare("_", "1"),-1)

        assertEquals( KeySorter.compare("_", "_"),0)

        assertEquals( KeySorter.compare("_", "A"),-1)
        assertEquals( KeySorter.compare("_", "B"),-1)

        assertEquals( KeySorter.compare("_", "a"),1)
        assertEquals( KeySorter.compare("_", "b"),1)
    }

    @Test
    fun `other char symbols - doesn"t matter`() {
        assertEquals( KeySorter.compare("!", "!"),0)
        assertEquals( KeySorter.compare("!", "a"),0)

        assertEquals( KeySorter.compare("0", "!"),1)
        assertEquals( KeySorter.compare("a", "!"),1)
        assertEquals( KeySorter.compare("A", "!"),1)
        assertEquals( KeySorter.compare("_", "!"),1)
    }
}