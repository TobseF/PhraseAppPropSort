package de.tfr.tools.propsort.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.paps.PhraseApi

class ApiResponseTest {


    @Test
    fun findIDTest() {
        val response =
            """[{"id":"80b96cb65fa640860fc4d4c7ab110cb9","name":"test-key","description":null,"name_hash":"93af16140b5ceaf1fa07b6728db74b62","plural":false,"max_characters_allowed":0,"tags":["messages"],"created_at":"2022-07-11T17:47:04Z","updated_at":"2022-07-11T17:47:08Z"}]"""
        val api = PhraseApi("")
        assertEquals("80b96cb65fa640860fc4d4c7ab110cb9", api.findId(response, "test-key"))
    }
}