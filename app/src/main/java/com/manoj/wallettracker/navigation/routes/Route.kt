package com.manoj.wallettracker.navigation.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.manoj.wallettracker.constants.AppConstants

object Routes {
    object ADD_EDIT_EXPENSE {
        const val route: String = "add_edit/{${AppConstants.ARG_TRANSACTION_ID}}"
        val arguments = listOf(
            navArgument(AppConstants.ARG_TRANSACTION_ID) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    }

    const val SPLASH = "splash"
    const val HOME = "home"
    const val CATEGORY = "select-category"
    const val GRAPH = "graph_screen"
    const val BIOMETRIC = "biometric_screen"
    const val ALL_TRANSACTIONS = "all_transactions"
}