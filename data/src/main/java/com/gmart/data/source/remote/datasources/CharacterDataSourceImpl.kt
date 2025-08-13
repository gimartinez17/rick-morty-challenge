package com.gmart.data.source.remote.datasources

import com.gmart.data.models.RnMCharacterDto
import com.gmart.data.source.remote.RickAndMortyApiService
import javax.inject.Inject

class CharacterDataSourceImpl @Inject constructor(
    private val apiService: RickAndMortyApiService
) : CharacterDataSource {

    override suspend fun getCharacters(): List<RnMCharacterDto> {
        val response = apiService.getAllCharacters()
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            throw Exception("HTTP error ${response.code()} ${response.message()}")
        }
    }
}