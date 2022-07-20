package tools.paps

import io.klogging.noCoLogger
import tools.paps.PhraseAppParser.getPhraseAppConfigFile
import tools.paps.PropertyFileType.listPropertyFiles
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files

private val logger = noCoLogger("BackupJob")

private const val defaultTempDir = "phrase_tool"

/**
 * @param subFolder subFolder in temp-dir
 */
fun backupFile(file: File, subFolder: String?) {
    val tempDir = createAndGetTempDir(subFolder)   //could be replaced by createTempDirectory
    val destPath = """${tempDir.absolutePath}\${file.name}"""
    val destFile = File(destPath)
    logger.info { "Backing up file from " + file.absolutePath + " to " + destFile.absolutePath }
    Files.copy(file.toPath(), destFile.toPath())
}

private fun clearTempDir(subDir: String = defaultTempDir) {
    val tempDir = createAndGetTempDir(subDir)
    val files = tempDir.listFiles()
    logger.info { "Deleting ${files?.size} files im temp folder: ${tempDir.absolutePath}" }
    files?.forEach { it.delete() }
}

fun createAndGetTempDir(subDir: String? = defaultTempDir): File {
    val subPath = if (subDir == null) "" else "//$subDir"
    val tempDir = File(System.getProperty("java.io.tmpdir") + subPath)
    if (!tempDir.isDirectory) {
        logger.info { "Creating temp dir: ${tempDir.absolutePath}" }
        tempDir.mkdir()
    }
    return tempDir
}

fun backupFiles(args: Array<String>) {
    if (args.size == 2) {
        backupFiles(args.toFile(1))
    } else if (args.size == 3) {
        val tempSubDir = args[2]
        logger.info { "Using custom sub subdirectory: $tempSubDir" }
        backupFiles(args.toFile(1), tempSubDir)
    } else {
        logger.info { "Running backup job from current directory" }
        backupFiles()
    }
}

fun backupFiles(sourceDir: File = File(""), tempSubDir: String = defaultTempDir) {
    clearTempDir(tempSubDir)
    if (PhraseAppParser.hasPhraseAppConfig(sourceDir)) {
        logger.info { "Using PhraseApp config file: " + sourceDir.getPhraseAppConfigFile() }
        PhraseAppParser.parse(sourceDir).flatMap { backupDirectory(it) }.forEach { file -> backupFile(file, tempSubDir) }
    } else {
        logger.info { "Couldn't find PhraseApp config file: " + sourceDir.absolutePath }
    }
}

private fun backupDirectory(directory: File): Set<File> {
    return listPropertyFiles(directory)
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
        logger.error { "Comparing directories needs at least two arguments: local-dir & remote-dir " }
    }
    logger.info { "Comparing directories: \"" + args[1] + "\" and \"" + args[2] + "\"" }
    compareDirectories(File(args[1]), File(args[2]))
}

fun compareDirectories(fileA: File, fileB: File) {
    val localKeys = listKeys(fileA)
    val remoteKeys = listKeys(fileB).toMutableSet()
    remoteKeys.removeAll(localKeys)
    logger.info { "The following keys are unused remote keys: " + remoteKeys.joinToString(System.lineSeparator()) }
}


