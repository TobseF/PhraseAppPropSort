package tools.paps

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.isSuccessful
import io.klogging.NoCoLogging
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import tools.paps.util.JsonParser

class PhraseApi(val accessToken: String) : NoCoLogging {

    private val api = "https://api.phrase.com/v2/"
    private val userAgent = "ITscope Snc"

    private fun projectsPath(projectID: String) = api + "projects/$projectID/"

    fun deleteKey(projectID: String, keyId: String) {
        val path = projectsPath(projectID) + "keys/$keyId"
        val (_, response, _) = Fuel.delete(path).authenticate().setUserAgent().response()
        if (!response.isSuccessful) {
            logger.error { "Failed deleting key: $keyId (${response.statusCode})" }
        }
    }

    fun findKeyId(projectID: String, keyName: String): String? {
        val path = projectsPath(projectID) + "keys?q=name:$keyName"
        val (_, _, result) = Fuel.get(path).authenticate().setUserAgent().responseString()
        // .responseObject<FindKeyResponse>() TODO: User Fuel mapping
        return findId(result.get(), keyName)
    }


    private fun Request.authenticate() = this.authentication().bearer(accessToken)

    private fun Request.setUserAgent() = apply {
        this.headers["User-Agent"] = userAgent
    }

    fun findId(resultJson: String, keyName: String): String? {
        if (resultJson == "[]") {
            logger.warn { "Cannot find key: $keyName" }
            return null
        }
        try {
            val response: List<KeyResponse> = JsonParser.readJson(resultJson)
            if (response.size > 1) {
                logger.error { "Found more than one entry: $resultJson" }
            }
            if (response.isEmpty()) {
                logger.warn { "Failed finding id for key: $keyName" }
                return null
            }
            return response.first().id
        } catch (e: SerializationException) {
            logger.error(e) { "Cannot parse FindKeyResponse from JSON: $resultJson" }
            return null
        }
    }

    @Serializable
    data class KeyResponse(
        val id: String,
        val name: String,
        val description: String? = null,

        @SerialName("name_hash")
        val nameHash: String,

        val plural: Boolean,

        @SerialName("max_characters_allowed")
        val maxCharactersAllowed: Long,

        val tags: List<String>,

        @SerialName("created_at")
        val createdAt: String,

        @SerialName("updated_at")
        val updatedAt: String
    )

}