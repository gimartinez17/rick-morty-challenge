package com.gmart.domain.usecases

import com.gmart.domain.entities.RnMCharactersResponse
import com.gmart.domain.repositories.CharactersRepository

class GetCharactersUseCases(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(): RnMCharactersResponse = charactersRepository.getCharacters()
}