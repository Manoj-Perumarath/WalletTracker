package com.manoj.wallettracker.presentation.home.effect

sealed interface HomeEffect {
    data object Initial : HomeEffect
}