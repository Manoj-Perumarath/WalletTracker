package com.manoj.wallettracker.presentation.splash.vm

import androidx.lifecycle.viewModelScope
import com.manoj.wallettracker.data.ui.splash.repo.SplashRepository
import com.manoj.wallettracker.foundation.core.vm.BaseViewModel
import com.manoj.wallettracker.presentation.splash.action.SplashAction
import com.manoj.wallettracker.presentation.splash.event.SplashEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(repository: SplashRepository) :
    BaseViewModel<Unit, SplashAction, SplashEffect, SplashRepository>(Unit, repository) {

    init {
        dispatch(SplashAction.AppLaunch)
    }

    override fun dispatch(action: SplashAction) {
        when (action) {
            is SplashAction.AppLaunch -> {
                viewModelScope.launch {
                    setEffect(SplashEffect.NavigateToHome)
                }
            }
        }
    }
}