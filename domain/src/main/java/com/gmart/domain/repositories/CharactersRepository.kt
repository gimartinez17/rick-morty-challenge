package com.gmart.domain.repositories

import com.gmart.domain.entities.RnMCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(): Flow<List<RnMCharacter>>
}