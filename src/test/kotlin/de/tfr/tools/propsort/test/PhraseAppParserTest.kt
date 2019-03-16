package de.tfr.tools.propsort.test

import org.junit.Assert.assertTrue
import org.junit.Test
import tools.paps.PhraseAppParser
import java.io.File


class PhraseAppParserTest {
    private val resourcesPath = "src/test/resources/"

    @Test
    fun hasPhraseAppConfig() {
        assertTrue(PhraseAppParser.hasPhraseAppConfig(File(resourcesPath)))
    }

    @Test
    fun parseResources() {
        PhraseAppParser.parse(File(resourcesPath))
    }

}