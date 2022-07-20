package tools.paps.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tools.paps.PhraseApi

object JsonParser {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @ExperimentalSerializationApi
    fun readJson(string: String): List<PhraseApi.KeyResponse> {
        return json.decodeFromString(string)
    }

}