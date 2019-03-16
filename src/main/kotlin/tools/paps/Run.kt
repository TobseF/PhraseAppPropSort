package tools.paps

import tools.paps.PhraseAppParser.getPhraseAppConfigFile
import tools.paps.VersionCheck.VersionCheckResult.State.NewVersion
import java.io.File
import java.util.Arrays.asList

fun main(args: Array<String>) {
    checkForUpdates()
    val params = if (args.isEmpty()) arrayOf(".") else args

    if ("-compare" == params[0]) {
        compareDirectories(params)
    } else if ("-backup" == params[0]) {
        backupFiles(params)
    } else {
        formatFiles(params)
    }
}

private fun checkForUpdates() {
    val versionCheck = VersionCheck.checkForUpdates()
    if (versionCheck.state === NewVersion) {
        println("Propsort new version available: v" + versionCheck.currentVersion + " -> " + versionCheck.newVersion)
    } else {
        println("Propsort v" + versionCheck.currentVersion)
    }
}

private fun formatFiles(files: Array<String>) {
    for (arg in files) {
        val file = File(arg)
        if (file.isFile) {
            formatFile(file)
        } else if (file.isDirectory) {
            if (PhraseAppParser.hasPhraseAppConfig(file)) {
                println("Using PhraseApp config file: " + file.getPhraseAppConfigFile())
                PhraseAppParser.parse(file).forEach { formatFolder(it) }
            } else {
                formatFolder(file)
            }
        }
    }
}

private fun formatFolder(folder: File) {
    val filesInFolder = folder.listFiles()
    println("Formatted files in directory: " + folder.absolutePath)
    if (filesInFolder != null) {
        val files = asList<File>(*filesInFolder)
        files.filter { file -> !file.exists() }.forEach { file -> println("Could`t read file: $file") }
        files.stream().filter(PropertyFileType.isPropertyFile).forEach { formatFile(it) }
    } else {
        System.err.print("Couldn't list properties in directory: $folder")
    }
}

private fun formatFile(file: File) {
    try {
        val parser = MessagePropertyParser(file)
        val parsed = parser.parse()
        val formatted = parsed.formatted()
        if (parsed == formatted) {
            println("File: '${file.name}':  Already formatted (nothing to do) ")
        } else {
            file.write(formatted)
            println("File: '${file.name}':  Successful formatted!")
        }
    } catch (e: Exception) {
        System.err.print("Couldn't format file: $file")
        e.printStackTrace()
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