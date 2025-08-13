package com.gmart.rickandmorty.di

import com.gmart.data.repositories.CharacterRepositoryImpl
import com.gmart.domain.usecases.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Singleton
    @Provides
    fun providesCharactersUseCase(repository: CharacterRepositoryImpl): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }
}