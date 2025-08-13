package com.gmart.rickandmorty.ui.features.characters

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gmart.domain.usecases.GetCharactersUseCase
import com.gmart.rickandmorty.di.annotations.IoDispatcher
import com.gmart.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel<CharactersViewState, CharactersEvent, Unit>(dispatcher) {


    override fun setInitialState(): CharactersViewState = CharactersViewState()


    override fun handleEvents(event: CharactersEvent) {
        when (event) {
            is CharactersEvent.LoadCharacters -> loadCharacters()
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch(dispatcher) {
            getCharactersUseCase.invoke().onStart {
                updateViewState { copy(isLoading = true, error = null) }
            }.catch {
                Log.e("CharactersViewModel", "Error loading characters: ${it.localizedMessage}")
                updateViewState {
                    copy(isLoading = false, error = "Something went wrong")
                }
            }.collect { response ->
                if (response.isEmpty()) {
                    updateViewState {
                        copy(isLoading = false, error = "No characters found")
                    }
                } else {
                    updateViewState {
                        copy(isLoading = false, characters = response)
                    }
                }
            }
        }
    }
}