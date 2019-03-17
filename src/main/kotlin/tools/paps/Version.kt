package tools.paps

import java.io.File


object Version {
    private const val filePath = "/version.info"
    private const val resources = "src/main/resources"

    val version: String by lazy { filePath.readTextFromFile() }

    /**
     *  Load file from `jar` or from `resources`dir. So it works locally or as packed as jar.
     */
    private fun String.readTextFromFile(): String {
        return javaClass.getResource(this)?.readText() ?: File(resources + this).reader().readText()
    }
}
