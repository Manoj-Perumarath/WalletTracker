package com.manoj.wallettracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.manoj.wallettracker.navigation.MainNavHost
import com.manoj.wallettracker.ui.theme.WalletTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WalletTracker)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalletTrackerTheme {
                MainNavHost()
            }
        }
    }
}
