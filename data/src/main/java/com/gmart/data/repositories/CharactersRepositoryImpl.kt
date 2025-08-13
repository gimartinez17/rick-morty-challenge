package com.gmart.data.repositories

import com.gmart.data.source.remote.datasources.CharacterDataSource
import com.gmart.domain.repositories.CharactersRepository
import com.gmart.mappers.mapToDomain
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterDataSource
) : CharactersRepository {

    override suspend fun getAllCharacters() = flow {
        emit(remoteDataSource.getCharacters().map { it.mapToDomain() })
    }
}