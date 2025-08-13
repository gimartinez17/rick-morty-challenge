package com.gmart.rickandmorty.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<ViewState, Event, Effect>(private val dispatcher: CoroutineDispatcher) :
    ViewModel() {

    private val initialState: ViewState by lazy { setInitialState() }
    abstract fun setInitialState(): ViewState

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch(dispatcher) {
            _event.collect { handleEvents(it) }
        }
    }

    abstract fun handleEvents(event: Event)

    protected suspend fun setViewState(state: ViewState) {
        _viewState.emit(state)
    }

    protected fun updateViewState(update: ViewState.() -> ViewState) {
        _viewState.update { update(it) }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch(dispatcher) { _event.emit(event) }
    }

    protected fun setEffect(effectValue: Effect) {
        viewModelScope.launch(dispatcher) {
            _effect.send(effectValue)
        }
    }
}