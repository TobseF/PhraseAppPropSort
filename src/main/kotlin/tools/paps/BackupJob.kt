package tools.paps

import tools.paps.PhraseAppParser.getPhraseAppConfigFile
import tools.paps.PropertyFileType.listPropertyFiles
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files


fun backupFile(file: File, folder: String?) {
    val tempDir = getTempDir(folder)
    val destPath = """${tempDir.absolutePath}\${file.name}"""
    val destFile = File(destPath)
    println("Backing up file from " + file.absolutePath + " to " + destFile.absolutePath)
    Files.copy(file.toPath(), destFile.toPath())
}

private fun clearTempDir() {
    val tempDir = getTempDir()
    val files = tempDir.listFiles()
    println("Deleting ${files.size} files im temp folder: $tempDir")
    files?.forEach { it.delete() }
}

private fun getTempDir(): File {
    val tempDir = File(System.getProperty("java.io.tmpdir") + "/propsort")
    if (!(tempDir.exists() && tempDir.isDirectory)) {
        println("Creating temp dir: $tempDir")
        tempDir.mkdir()
    }
    return tempDir
}


fun backupFiles(args: Array<String>) {
    clearTempDir()
    val dir = File(".")
    if (PhraseAppParser.hasPhraseAppConfig(dir)) {
        println("Using PhraseApp config file: " + dir.getPhraseAppConfigFile())
        val folder = if (args.size == 2) args[1] else null
        PhraseAppParser.parse(dir).flatMap { backupDirectory(it) }.forEach { file -> backupFile(file, folder) }
    } else {
        println("Couldn't find PhraseApp config file: " + dir.absolutePath)
    }
}

private fun backupDirectory(directory: File): Set<File> {
    return PropertyFileType.listPropertyFiles(directory)
}

private fun getTempDir(folder: String?): File {
    val tempDir = File(getTempDir(folder).absolutePath + "\\" + (folder ?: ""))
    if (!(tempDir.exists() && tempDir.isDirectory)) {
        println("Creating temp dir: $tempDir")
        tempDir.mkdir()
    }
    return tempDir
}

fun listKeys(file: File): Set<String> {
    return listPropertyFiles(file).flatMap { collectKeys(it) }.toSet()
}

private fun collectKeys(file: File): Set<String> {
    return try {
        MessagePropertyParser(file).parse().map { it.key }.toSet()
    } catch (e: FileNotFoundException) {
        emptySet()
    }

}

fun compareDirectories(args: Array<String>) {
    if (args.size != 3) {
        println("Comparing directories needs at least two arguments: local-dir & remote-dir ")
    }
    println("Comparing directories: \"" + args[1] + "\" and \"" + args[2] + "\"")
    val localKeys = listKeys(File(args[1]))
    val remoteKeys = listKeys(File(args[2])).toMutableSet()
    remoteKeys.removeAll(localKeys)
    println("The following keys are unused remote keys: ")
    remoteKeys.forEach { println(it) }
}

