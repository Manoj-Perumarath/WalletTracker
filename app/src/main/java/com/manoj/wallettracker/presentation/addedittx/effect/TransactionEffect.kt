package com.manoj.wallettracker.data.ui.addedittx.effect

sealed interface TransactionEffect {
    data object ClosePage : TransactionEffect
    data object ShowKeyboard : TransactionEffect
}
