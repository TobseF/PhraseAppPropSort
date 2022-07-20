package tools.paps

import io.klogging.noCoLogger
import tools.paps.PhraseAppParser.getPhraseAppConfigFile
import tools.paps.parser.prhaseapp.config.ConfigFile
import java.io.File

private val logger = noCoLogger("DeleteJob")

enum class Mode { Delete, Simulate }

fun deleteUnusedKeysByConfig(configDir: File = File("."), remoteDir: File = createAndGetTempDir(), mode: Mode = Mode.Delete) {
    val configFile = configDir.getPhraseAppConfigFile()
    val messageFolders: Set<File> = PhraseAppParser.parse(configDir)
    if (messageFolders.isEmpty()) {
        logger.warn { "Found no message folder in config file $configFile" }
    }
    if (messageFolders.size > 1) {
        logger.warn { "Found more than one message folder: $messageFolders" }
    }
    val messageFolder = messageFolders.first()
    if (!messageFolder.exists()) {
        logger.error { "ERROR: Specified config directory not available: $messageFolder" }
    }
    deleteUnusedKeys(messageFolder, remoteDir, configFile, mode)
}


fun deleteUnusedKeys(localDir: File = File("."), remoteDir: File = createAndGetTempDir(), configFile: File, mode: Mode) {
    val resourceBundesLocal: MessageResourceBundles = localDir.loadMessageResourceBundles()
    val resourceBundesRemote: MessageResourceBundles = remoteDir.loadMessageResourceBundles()

    val propertiesToDelete = mutableListOf<PropertyFileLine>()
    resourceBundesLocal.tags.forEach { tag ->
        val localByTag: List<PropertyFileLine> = resourceBundesLocal.listByTag(tag)
        val localKeys = localByTag.map { it.key }
        val remoteByTag: List<PropertyFileLine> = resourceBundesRemote.listByTag(tag)

        val unusedLines = remoteByTag.filter { !localKeys.contains(it.key) }
        propertiesToDelete.addAll(unusedLines)
    }

    val keysToDelete = propertiesToDelete.map { it.key }.toSet()
    logger.info { "Keys to delete: \n" + keysToDelete.joinToString(System.lineSeparator()) }
    val phraseYML = ConfigFile.fromYML(configFile)
    val accessToken = phraseYML.phraseapp.accessToken
    val projectId = readProjectId(phraseYML)

    val phraseApi = PhraseApi(accessToken)

    keysToDelete.forEach { key ->
        val keyId = phraseApi.findKeyId(projectId, key)
        if (keyId != null && mode == Mode.Delete) {
            logger.info { "Deleting key: $key ($keyId)" }
            phraseApi.deleteKey(projectId, keyId)
        }
    }

}

private fun readProjectId(configFile: ConfigFile): String {
    return getTaggedSources(configFile).first().projectID
}

private fun getTaggedSources(configFile: ConfigFile) =
    configFile.phraseapp.push.sources.filter { it.file.contains("<tag>") }


fun File.loadMessageResourceBundles() = MessageResourceBundles(this.walk().filter(PropertyFileType.isPropertyFile).toList())


class MessageResourceBundles(propertyFiles: List<File>) {

    private val properties: List<PropertyFileLine>
    val tags: Set<String>
    private val locales: Set<String>

    init {
        fun parse(file: File): List<PropertyFileLine> {
            val tag = file.getTag()
            val locale = file.getLocale()
            return MessagePropertyParser(file).parse().map {
                PropertyFileLine(locale, tag, it.key, it.value)
            }
        }

        properties = propertyFiles.flatMap { parse(it) }
        tags = properties.map { it.tag }.toSet()
        locales = properties.map { it.locale }.toSet()
    }

    override fun toString(): String {
        return properties.joinToString(separator = System.lineSeparator())
    }

    fun listByTag(tag: String) = properties.filter { it.tag == tag }
    fun listByLocales(locale: String) = properties.filter { it.locale == locale }
}

fun File.nameWithoutExtension() = this.name.substringBeforeLast(".")

fun File.getTag() = this.nameWithoutExtension().split("_")[0]
fun File.getLocale() = this.nameWithoutExtension().split("_")[1]

data class PropertyFileLine(val locale: String, val tag: String = "messages", override val key: String, override val value: String?) : Property(key, value) {
    override fun toString(): String {
        return "$tag,$locale,$key,$value"
    }
}