package com.gmart.domain.usecases

import com.gmart.domain.repositories.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke() = charactersRepository.getAllCharacters()
}