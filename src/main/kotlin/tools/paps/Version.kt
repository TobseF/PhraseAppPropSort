package tools.paps

import java.io.File


object Version {
    private const val filePath = "version.info"

    val version: String by lazy { filePath.readTextFromFile() }

    private fun String.readTextFromFile(): String {
        return javaClass.getResource(this)?.readText() ?: File(this).reader().readText()
    }
}
