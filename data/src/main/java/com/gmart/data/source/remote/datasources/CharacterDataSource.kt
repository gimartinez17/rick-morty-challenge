package com.gmart.data.source.remote.datasources

import com.gmart.data.models.RnMCharacterDto

interface CharacterDataSource {
    suspend fun getCharacters(): List<RnMCharacterDto>
}