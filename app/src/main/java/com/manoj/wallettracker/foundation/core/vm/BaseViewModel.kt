package com.manoj.wallettracker.foundation.core.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//abstract class BaseViewModel<STATE, EFFECT, ACTION, ENVIRONMENT>(
abstract class BaseViewModel<STATE, ACTION, EFFECT, REPOSITORY>(
    initialState: STATE,
    protected val repository: REPOSITORY
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    private val _effect: MutableStateFlow<EFFECT?> = MutableStateFlow(null)

    val state: StateFlow<STATE> = _state.asStateFlow()
    val effect: StateFlow<EFFECT?> = _effect.asStateFlow()

    abstract fun dispatch(action: ACTION)

    protected fun setState(newState: STATE.() -> STATE) {
        _state.update(newState)
    }

    protected fun setEffect(newEffect: EFFECT) {
        _effect.update { newEffect }
    }

    private fun stateValue(): STATE {
        return state.value
    }

}
