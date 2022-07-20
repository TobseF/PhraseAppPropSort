package tools.paps.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JsonParserTest {

    @Test
    fun parseFindKeyResponseFormatted() {
        val response =
            """[
              {
                "id": "67ff1b4ce21e337bb7fd4904057a6ad9",
                "name": "ProductPortal_Recommender_BestOffers",
                "description": null,
                "name_hash": "f452c056158bc7418bf1113af7a117e2",
                "plural": false,
                "max_characters_allowed": 0,
                "tags": [
                  "messages",
                  "upload-20200527_115409"
                ],
                "created_at": "2019-06-09T14:38:46Z",
                "updated_at": "2022-02-09T12:21:35Z"
              }
            ]"""
        val decoded = JsonParser.readJson(response)
        assertEquals(decoded[0].id, "67ff1b4ce21e337bb7fd4904057a6ad9")
        assertEquals(decoded[0].name, "ProductPortal_Recommender_BestOffers")
    }

    @Test
    fun parseFindKeyResponse() {
        val response =
            """[{"id":"80b96cb65fa640860fc4d4c7ab110cb9","name":"Product_StreetPrice_Caption_Netto","description":null,"name_hash":"93af16140b5ceaf1fa07b6728db74b62","plural":false,"max_characters_allowed":0,"tags":["messages"],"created_at":"2022-07-11T17:47:04Z","updated_at":"2022-07-11T17:47:08Z"}]"""
        val decoded = JsonParser.readJson(response)
        assertEquals(decoded[0].id, "80b96cb65fa640860fc4d4c7ab110cb9")
        assertEquals(decoded[0].name, "Product_StreetPrice_Caption_Netto")
    }
}