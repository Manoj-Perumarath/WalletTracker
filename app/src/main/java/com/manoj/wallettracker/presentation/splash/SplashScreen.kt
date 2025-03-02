package com.manoj.wallettracker.presentation.splash

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.manoj.wallettracker.foundation.ext.TriggerEvent
import com.manoj.wallettracker.navigation.routes.Routes
import com.manoj.wallettracker.presentation.splash.event.SplashEffect
import com.manoj.wallettracker.presentation.splash.vm.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {

    TriggerEvent(
        viewModel = viewModel,
    ) {
        when (it) {
            SplashEffect.NavigateToHome -> {
//                navController.navigate(Routes.HOME) {
//                    popUpTo(Routes.SPLASH) {
//                        inclusive = true
//                    }
//                }
                navController.navigate(Routes.BIOMETRIC) {
                    popUpTo(Routes.SPLASH) {
                        inclusive = true
                    }
                }
            }

        }
    }
}