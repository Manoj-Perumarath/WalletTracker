package com.manoj.wallettracker.presentation.auth

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.manoj.wallettracker.navigation.routes.Routes

@SuppressLint("ContextCastToActivity")
@Composable
fun BiometricAuthScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    var isAuthenticated by remember { mutableStateOf(false) }

    if (canAuthenticate(context) && !isAuthenticated) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login")
            .setSubtitle("Log using your biometric credential")
            .setNegativeButtonText("Use account password").build()

        val biometricPrompt = createBiometricPrompt(
            context,
            navHostController,
            onAuthSuccess = { isAuthenticated = true })
        biometricPrompt.authenticate(promptInfo)
    }
}

fun createBiometricPrompt(
    context: Context, navHostController: NavHostController, onAuthSuccess: () -> Unit
): BiometricPrompt {
    val executor = ContextCompat.getMainExecutor(context)

    val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onAuthSuccess()
            navHostController.navigate(Routes.HOME) {
                popUpTo(Routes.BIOMETRIC) {
                    inclusive = true
                }
            }
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
//            Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show()
        }
    }

    return BiometricPrompt(context as AppCompatActivity , executor, callback)

}