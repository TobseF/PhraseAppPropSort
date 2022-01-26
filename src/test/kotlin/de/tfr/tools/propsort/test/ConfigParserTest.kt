package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.paps.parser.prhaseapp.config.ConfigFile
import java.io.File

class ConfigParserTest {

    private val resourcesPath = "src/test/resources/"
    private val propertiesFile = "$resourcesPath.phraseapp.yml"

    @Test
    fun `Read Phrase YML config file`() {
        val phraseConfig = ConfigFile.Companion.fromYML(File(propertiesFile))
        assertEquals(phraseConfig.phraseapp.accessToken, "5d335549ab4994a3b5543eae727e3b9b49c2bdeddcdec5b735e5fa5595f5cf9e")
        assertEquals(phraseConfig.phraseapp.pull.targets[0].params.localeID, "29435ba7ff4a52a357e3939de2425d3c")
        assertEquals(phraseConfig.phraseapp.pull.targets[0].file, "./src/main/resources/de/project/messages/messages.properties")
    }
}