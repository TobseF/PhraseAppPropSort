package de.tfr.tools.propsort.test

import org.junit.Assert.assertEquals
import org.junit.Test
import tools.paps.MessagePropertyParser
import tools.paps.formatted
import java.io.File

class MessagePropertyParserFileTest {

    private val resourcesPath = "src/test/resources/"
    private val propertiesFile = resourcesPath + "messages.properties"
    private val expectedFile = resourcesPath + "messages_formatted.properties"

    @Test
    fun messagePropertyFormatterTest() {

        val expected = File(expectedFile).readLines()
        val lines = File(propertiesFile).readLines()
        val sorted = MessagePropertyParser(lines).parse().formatted()

        println("$propertiesFile:")


        assertEquals(expected.joinToString(), sorted.joinToString())
    }

    private fun List<String>.joinToString() = this.joinToString("\n")


}