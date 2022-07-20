package tools.paps

import io.klogging.Level
import io.klogging.noCoLogger
import tools.paps.PhraseAppParser.getPhraseAppConfigFile
import tools.paps.PropertyFileType.isPropertyFile
import tools.paps.VersionCheck.VersionCheckResult.State.NewVersion
import tools.paps.config.LogConfig.setLogConfig
import java.io.File
import java.lang.Thread.sleep
import kotlin.system.exitProcess

private val logger = noCoLogger("PhraseTool")

fun main(args: Array<String>) {
    setLogConfig(Level.INFO)
    checkForUpdates()

    var params = arrayOf(".")
    if (args.isEmpty()) {
        logger.info { "No folder provided, using current folder: " + File(".").absolutePath }
    } else {
        params = args
    }
    if ("-delete-unused" == params[0]) {
        deleteUnused(params, args)
    } else if ("-compare" == params[0]) {
        compareDirectories(params)
    } else if ("-backup" == params[0]) {
        backupFiles(params)
    } else {
        formatFiles(params)
    }
    shutdown()
}

private fun deleteUnused(params: Array<String>, args: Array<String>) {
    if ("-simulate" == params[1]) {
        if (args.size == 3) {
            deleteUnusedKeysByConfig(args.toFile(2), mode = Mode.Simulate)
        } else {
            deleteUnusedKeysByConfig(args.toFile(2), args.toFile(3), Mode.Simulate)
        }
    } else if (args.size > 2) {
        deleteUnusedKeysByConfig(args.toFile(1), args.toFile(2))
    } else {
        deleteUnusedKeysByConfig(args.toFile(1))
    }
}

fun Array<String>.toFile(index: Int): File {
    if (index >= this.size) {
        logger.error { "Parameter $index should be a file path, but was not present" }
        shutdown()
    }
    val file = File(this[index])
    if (!file.exists()) {
        logger.error { "File path is not present: $file" }
    }
    return file
}

private fun shutdown() {
    sleep(200) // Ensure we got all log messages
    logger.info { "Shutting down..." }
    sleep(200) // Ensure we see the Shutting down...
    exitProcess(0)
}

private fun checkForUpdates() {
    val logger = noCoLogger("VersionCheck")
    val versionCheck = VersionCheck.checkForUpdates()
    if (versionCheck.state === NewVersion) {
        logger.info { "Propsort new version available: v" + versionCheck.currentVersion + " -> " + versionCheck.newVersion }
    } else {
        logger.info { "Propsort v" + versionCheck.currentVersion }
    }
}

/**
 * @param files list of paths which can point
 *  1. A property-file to sort (`filename.properties`)
 *  2. A phrase config file (`.phraseapp.yml`)
 *  3. A folder which contains several property-files to sort
 */
private fun formatFiles(files: List<File>) {
    for (file in files) {
        if (file.isPropertyFile()) {
            formatFile(file)
        } else if (file.isDirectory) {
            if (PhraseAppParser.hasPhraseAppConfig(file)) {
                logger.info { "Using PhraseApp config file: " + file.getPhraseAppConfigFile() }
                PhraseAppParser.parse(file).forEach { formatFolder(it) }
            } else {
                formatFolder(file)
            }
        } else {
            logger.warn { "Provided path $file is not a property-file, .phraseapp.yml or folder" }
        }
    }
}

private fun formatFiles(files: Array<String>) {
    logger.info { "Starting format action" }
    if (files.isEmpty()) {
        logger.error { "No files to format specified " }
    } else {
        val filesToFormat = files.map { File(it) }
        logger.info {
            val paths = filesToFormat.joinToString(";") { it.absolutePath }
            "Files to format: $paths}"
        }
        formatFiles(filesToFormat)
    }
}

private fun formatFolder(folder: File) {
    val filesInFolder = folder.listFiles()
    logger.info { "Formatted files in directory: " + folder.absolutePath }
    if (filesInFolder != null) {
        val files = listOf(*filesInFolder)
        files.filter { file -> !file.exists() }.forEach { file -> logger.warn { "Could`t read file: $file" } }
        files.stream().filter(isPropertyFile).forEach { formatFile(it) }
        if (files.isEmpty()) {
            logger.error("Provided folder does not contain any property file to sort: $folder")
        }
    } else {
        logger.error { "Couldn't list properties in directory: $folder" }
    }
}

private fun formatFile(file: File) {
    try {
        val parser = MessagePropertyParser(file)
        val parsed = parser.parse()
        val formatted = parsed.formatted()
        if (parsed == formatted) {
            logger.info { "File: '${file.name}':  Already formatted (nothing to do) " }
        } else {
            file.write(formatted)
            logger.info { "File: '${file.name}':  Successful formatted!" }
        }
    } catch (e: Exception) {
        logger.error(e) { "Couldn't format file: $file" }
    }

}

private fun File.write(lines: List<String>) {
    val writer = this.bufferedWriter()
    for (line in lines) {
        writer.write(line)
        writer.newLine()
    }
    writer.close()
}