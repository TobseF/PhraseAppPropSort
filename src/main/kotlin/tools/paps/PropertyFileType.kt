package tools.paps

import java.io.File
import java.util.*

import java.util.Collections.emptySet

object PropertyFileType {
    private const val fileExtension = ".properties"
    val isPropertyFile = { prop: File -> prop.isFile && prop.name.lowercase(Locale.getDefault()).endsWith(fileExtension) }

    fun File.isPropertyFile() = isPropertyFile.invoke(this)

    fun listPropertyFiles(folder: File): Set<File> {
        assert(folder.isDirectory)
        val files = folder.listFiles()
        return files?.filter(isPropertyFile)?.toSet() ?: emptySet()
    }
}
