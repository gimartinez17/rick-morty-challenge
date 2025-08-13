package com.gmart.rickandmorty.ui.features.characters

import com.gmart.domain.entities.RnMCharacter

data class CharactersViewState(
    val isLoading: Boolean = false,
    val characters: List<RnMCharacter> = emptyList(),
    val error: String? = null
)

sealed class CharactersEvent {
    object LoadCharacters : CharactersEvent()
}
