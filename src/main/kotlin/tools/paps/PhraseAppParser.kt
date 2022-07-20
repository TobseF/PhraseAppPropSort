package tools.paps

import java.io.File

/**
 * Parses a `.phraseapp.yml` and reads files, listed there.
 */
object PhraseAppParser {

    private val phraseAppFileName = ".phraseapp.yml"

    fun hasPhraseAppConfig(dir: File): Boolean {
        return dir.getPhraseAppConfigFile().exists()
    }

    fun File.getPhraseAppConfigFile(): File {
        return File(this.absolutePath, phraseAppFileName)
    }

    fun parse(dir: File): Set<File> =
        dir.getPhraseAppConfigFile().readLines().asSequence()
            .filter { it.contains("file:") }
            .map { it.substringAfterLast(":").trim() }
            .map { file -> getAbsoluteDir(dir, file) }
            .map { File(it) }
            .toSet()

    private fun getAbsoluteDir(dir: File, file: String): String {
        val fileName = dir.absolutePath.replace("\\.", "") + "/" + file.replace("./", "").removeSurrounding("\"")
        return fileName.removeSuffix(fileName.substringAfterLast("/"))
    }

}
