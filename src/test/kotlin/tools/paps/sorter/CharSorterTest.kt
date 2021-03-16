package tools.paps.sorter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tools.paps.VersionCheck

/**
 * Test for [CharSorter]
 */
internal class CharSorterTest{

    @Test
    fun `compare digits`() {
        assertEquals( CharSorter.compare('0', '0'),0)
        assertEquals( CharSorter.compare('1', '1'),0)

        assertEquals( CharSorter.compare('1', '2'),-1)

        assertEquals( CharSorter.compare('1', 'a'),-1)
        assertEquals( CharSorter.compare('1', 'A'),-1)
        assertEquals( CharSorter.compare('0', '_'),-1)
    }

    @Test
    fun `compare uppercase letters`() {
        assertEquals( CharSorter.compare('A', 'A'),0)
        assertEquals( CharSorter.compare('A', 'B'),-1)
        assertEquals( CharSorter.compare('B', 'A'),1)

        assertEquals( CharSorter.compare('B', 'a'),-1)
        assertEquals( CharSorter.compare('B', 'b'),-1)
        assertEquals( CharSorter.compare('C', 'b'),-1)

        assertEquals( CharSorter.compare('A', '0'),1)
        assertEquals( CharSorter.compare('A', '1'),1)
        assertEquals( CharSorter.compare('A', '_'),-1)
    }

    @Test
    fun `compare lowercase letters`() {
        assertEquals( CharSorter.compare('a', 'a'),0)
        assertEquals( CharSorter.compare('a', 'b'),-1)
        assertEquals( CharSorter.compare('b', 'a'),1)

        assertEquals( CharSorter.compare('a', 'A'),1)
        assertEquals( CharSorter.compare('a', 'B'),1)
        assertEquals( CharSorter.compare('b', 'A'),1)

        assertEquals( CharSorter.compare('b', 'A'),1)
        assertEquals( CharSorter.compare('b', 'B'),1)
        assertEquals( CharSorter.compare('c', 'B'),1)

        assertEquals( CharSorter.compare('a', '0'),1)
        assertEquals( CharSorter.compare('a', '1'),1)
        assertEquals( CharSorter.compare('a', '_'),1)
    }

    @Test
    fun `compare underscore`() {
        assertEquals( CharSorter.compare('_', '0'),1)
        assertEquals( CharSorter.compare('_', '1'),1)

        assertEquals( CharSorter.compare('_', '_'),0)

        assertEquals( CharSorter.compare('_', 'A'),1)
        assertEquals( CharSorter.compare('_', 'B'),1)

        assertEquals( CharSorter.compare('_', 'a'),-1)
        assertEquals( CharSorter.compare('_', 'b'),-1)
    }

    @Test
    fun `other symbols - doesn't matter`() {
        assertEquals( CharSorter.compare('!', '!'),0)
        assertEquals( CharSorter.compare('!', 'a'),0)

        assertEquals( CharSorter.compare('0', '!'),-1)
        assertEquals( CharSorter.compare('a', '!'),-1)
        assertEquals( CharSorter.compare('A', '!'),-1)
        assertEquals( CharSorter.compare('_', '!'),-1)
    }
}