package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.paps.MessagePropertyParser
import tools.paps.PhraseAppParser
import tools.paps.formatted
import java.io.File

@DisplayName("Message property file parser")
class MessagePropertyParserFileTest {

    private val resourcesPath = "src/test/resources/"
    private val propertiesFile = resourcesPath + "messages.properties"
    private val expectedFile = resourcesPath + "messages_formatted.properties"

    @Test
    fun `Format massage property file`() {

        val expected = File(expectedFile).readLines()
        val lines = File(propertiesFile).readLines()
        val sorted = MessagePropertyParser(lines).parse().formatted()

        println("$propertiesFile:")


        assertEquals(expected.joinToString(), sorted.joinToString())
    }

    @Test
    fun `Has PhraseApp config file`() {
        Assertions.assertTrue(PhraseAppParser.hasPhraseAppConfig(File(resourcesPath)))
    }

    @Test
    fun `Parse resources`() {
        PhraseAppParser.parse(File(resourcesPath))
    }

    private fun List<String>.joinToString() = this.joinToString("\n")


}