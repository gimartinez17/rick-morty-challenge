package com.gmart.rickandmorty.di

import com.gmart.data.source.remote.RickAndMortyApiService
import com.gmart.data.source.remote.datasources.CharacterDataSource
import com.gmart.data.source.remote.datasources.CharacterDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCharacterDataSource(
        apiServices: RickAndMortyApiService
    ): CharacterDataSource {
        return CharacterDataSourceImpl(apiServices)
    }
}