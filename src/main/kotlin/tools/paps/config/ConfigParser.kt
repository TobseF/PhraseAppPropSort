package tools.paps.parser.prhaseapp.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mamoe.yamlkt.Yaml
import java.io.File

/**
 * Model and parser for the Phrase XML config file format [Format options - .phrase.yml](https://help.phrase.com/help/examples).
 */
@Serializable
data class ConfigFile (
    val phraseapp: Phraseapp
) {
    companion object {
        fun fromYML(yaml: String) = Yaml.decodeFromString(serializer(), yaml)

        fun fromYML(yamlFile: File) = fromYML(yamlFile.readText())
    }
}

@Serializable
data class Phraseapp (
    @SerialName("access_token")
    val accessToken: String = "",

    @SerialName("file_format")
    val fileFormat: String = "",

    val push: Push,
    val pull: Pull
)

@Serializable
data class Pull (
    val targets: List<Target>
)

@Serializable
data class Target (
    val file: String = "",

    @SerialName("project_id")
    val projectID: String = "",

    val params: TargetParams
)

@Serializable
data class TargetParams (
    @SerialName("locale_id")
    val localeID: String = "",

    val tag: String? = null,
    val encoding: String = "UTF-8",

    @SerialName("format_options")
    val formatOptions: FormatOptions ? = null
)


@Serializable
data class FormatOptions (
    @SerialName("escape_meta_chars")
    val escapeMetaChars: Boolean = false,

    @SerialName("crlf_line_terminators")
    val crlfLineTerminators: Boolean = false
)

@Serializable
data class Push (
    val sources: List<Source>
)

@Serializable
data class Source (
    val file: String = "",

    @SerialName("project_id")
    val projectID: String = "",

    val params: SourceParams
)

@Serializable
data class SourceParams (
    @SerialName("locale_id")
    val localeID: String = "",

    @SerialName("update_translations")
    val updateTranslations: Boolean = false,

    @SerialName("skip_unverification")
    val skipUnverification: Boolean = false,

    @SerialName("skip_upload_tags")
    val skipUploadTags: Boolean = false,

    @SerialName("file_encoding")
    val fileEncoding: String
)