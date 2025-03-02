package com.manoj.wallettracker.foundation.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manoj.wallettracker.foundation.core.vm.BaseViewModel

@Composable
fun <STATE,ACTION,EFFECT,REPOSITORY> TriggerEvent(
    viewModel: BaseViewModel<STATE,ACTION,EFFECT,REPOSITORY>,
    handle: (EFFECT) -> Unit
) {
    val effect by viewModel.effect.collectAsStateWithLifecycle()
    LaunchedEffect(effect) {
        effect?.let {
            handle(it)
        }
    }
}
