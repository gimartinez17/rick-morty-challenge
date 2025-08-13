package com.gmart.rickandmorty.di

import com.gmart.data.repositories.CharacterRepositoryImpl
import com.gmart.data.source.remote.datasources.CharacterDataSource
import com.gmart.domain.repositories.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun providesCharactersRepository(
        remoteDataSource: CharacterDataSource
    ): CharactersRepository =
        CharacterRepositoryImpl(remoteDataSource = remoteDataSource)
}
