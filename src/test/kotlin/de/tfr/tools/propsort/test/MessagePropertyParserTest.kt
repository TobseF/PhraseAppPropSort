package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.paps.MessagePropertyParser
import tools.paps.Version
import tools.paps.formatted

@DisplayName("Message property parser")
class MessagePropertyParserTest {

    @Test
    fun `Sort keys`() {
        val props = """
            ab_prop =foo
            a_prop =foo
            aA_prop =foo
            aa_prop =foo
        """
        val formatter = MessagePropertyParser(props.inLines())

        val expected = """
            aA_prop = foo
            a_prop = foo
            aa_prop = foo
            ab_prop = foo
        """.trimIndent()

        assertEquals(expected, formatter.parse().formatted().joinToString())
    }

    @Test
    fun `Multiple line comments`() {
        val props = """
            #First description line
            #Second description line
            A_Key_With_MultiLineComment=difficult to explain
        """
        val formatter = MessagePropertyParser(props.inLines())

        val expected = """
            # First description line Second description line
            A_Key_With_MultiLineComment = difficult to explain
        """.trimIndent()

        assertEquals(expected, formatter.parse().formatted().joinToString())
    }

    @Test
    fun `Key without value`() {
        val props = """
            Key_Without_Value =
        """
        val formatter = MessagePropertyParser(props.inLines())

        assertEquals("", formatter.parse().formatted().joinToString())
    }

    @Test
    fun `Simple comment`() {
        val props = """
            #Additional description
            A_Key_With_Comment=Be careful
        """
        val formatter = MessagePropertyParser(props.inLines())

        val expected = """
            # Additional description
            A_Key_With_Comment = Be careful
        """.trimIndent()

        assertEquals(expected, formatter.parse().formatted().joinToString())
    }

    @Test
    fun `Read test version file`() {
        assertEquals("1.0.6", readFile("/version.info"))
    }

    private fun readFile(fileName: String) = Version::class.java.getResource(fileName).readText(Charsets.UTF_8)

    @Test
    fun `Duplicated properties with same value`() {
        val props = """
            Double_Key_With_SameValue=Value_1
            Double_Key_With_SameValue=Value_1
        """
        val formatter = MessagePropertyParser(props.inLines())

        val expected = """
            Double_Key_With_SameValue = Value_1
        """.trimIndent()

        assertEquals(expected, formatter.parse().formatted().joinToString())
    }


    @Test
    fun `Duplicated properties with different values`() {
        val props = """
            Double_Key_With_DifferentValue=Value_1
            Double_Key_With_DifferentValue=Value_2
        """
        val formatter = MessagePropertyParser(props.inLines())

        val expected = """
            Double_Key_With_DifferentValue = Value_1
            Double_Key_With_DifferentValue = Value_2
        """.trimIndent()

        assertEquals(expected, formatter.parse().formatted().joinToString())
    }

    private fun String.inLines() = this.trimIndent().lines()

    private fun List<String>.joinToString() = this.joinToString("\n")
}