package com.manoj.wallettracker.presentation.auth

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL


fun canAuthenticate(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> false
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> false
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false
        else -> false
    }
}