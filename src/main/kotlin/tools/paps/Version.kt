package tools.paps


object Version {
    private const val filePath = "/version.info"
    private const val missingVersionValue = "0.0.0"

    val version: String by lazy { filePath.readTextFromFile() }

    private fun String.readTextFromFile(): String {
        return Version::class.java.getResource(this)?.readText(Charsets.UTF_8) ?: missingVersionValue
    }
}
