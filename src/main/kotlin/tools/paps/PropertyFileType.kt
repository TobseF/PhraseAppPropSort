package tools.paps

import java.io.File

import java.util.Collections.emptySet

object PropertyFileType {
    private const val fileExtension = ".properties"
    val isPropertyFile = { prop: File -> prop.name.toLowerCase().endsWith(fileExtension) }

    fun listPropertyFiles(folder: File): Set<File> {
        assert(folder.isDirectory)
        val files = folder.listFiles()
        return files?.filter(isPropertyFile)?.toSet() ?: emptySet()
    }
}
