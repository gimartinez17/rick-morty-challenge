package com.gmart.data.source.remote

import com.gmart.data.models.RnMCharactersResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<RnMCharactersResponseDto>
}