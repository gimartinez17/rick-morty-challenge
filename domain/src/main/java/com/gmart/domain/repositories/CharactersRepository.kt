package com.gmart.domain.repositories

import com.gmart.domain.entities.RnMCharactersResponse

interface CharactersRepository {
    suspend fun getCharacters(): RnMCharactersResponse
}