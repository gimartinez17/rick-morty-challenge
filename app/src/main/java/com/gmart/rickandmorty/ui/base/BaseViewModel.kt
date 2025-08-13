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

/**
 * BaseViewModel provides a template for implementing the MVI architecture in a ViewModel
 * using Kotlin Coroutines and Flow.
 *
 * @param ViewState The type representing the UI state.
 * @param Event The type representing user or UI events.
 * @param Effect The type representing one-time side-effects.
 * @param dispatcher The CoroutineDispatcher used for launching coroutines.
 */
abstract class BaseViewModel<ViewState, Event, Effect>(private val dispatcher: CoroutineDispatcher) :
    ViewModel() {

    private val initialState: ViewState by lazy { setInitialState() }

    /** Provides the initial state of the ViewModel. Must be implemented by subclasses. */
    abstract fun setInitialState(): ViewState

    /** StateFlow exposing the current UI state to the UI layer. */
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(initialState)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    /** SharedFlow for receiving events or intents from the UI. */
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    /** Flow emitting one-time effects such as navigation or messages. */
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    /** Collects incoming events and delegates handling to [handleEvents]. */
    private fun subscribeToEvents() {
        viewModelScope.launch(dispatcher) {
            _event.collect { handleEvents(it) }
        }
    }

    abstract fun handleEvents(event: Event)

    /**
     * Emits a new [ViewState].
     *
     * @param state New state to be emitted.
     */
    protected suspend fun setViewState(state: ViewState) {
        _viewState.emit(state)
    }

    /**
     * Updates the current [ViewState] using the provided lambda.
     *
     * @param update Lambda function that returns the updated state.
     */
    protected fun updateViewState(update: ViewState.() -> ViewState) {
        _viewState.update { update(it) }
    }

    /**
     * Updates the current [ViewState] using the provided lambda.
     *
     * @param update Lambda function that returns the updated state.
     */
    fun setEvent(event: Event) {
        viewModelScope.launch(dispatcher) { _event.emit(event) }
    }

    /**
     * Sends a one-time side-effect.
     *
     * @param effectValue Effect to send.
     */
    protected fun setEffect(effectValue: Effect) {
        viewModelScope.launch(dispatcher) {
            _effect.send(effectValue)
        }
    }
}