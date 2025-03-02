package com.manoj.wallettracker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manoj.wallettracker.constants.AppConstants
import com.manoj.wallettracker.navigation.routes.Routes
import com.manoj.wallettracker.presentation.addedittx.AddEditTransactionScreen
import com.manoj.wallettracker.presentation.addedittx.category.CategorySelectionScreen
import com.manoj.wallettracker.presentation.addedittx.vm.TransactionDetailViewModel
import com.manoj.wallettracker.presentation.alltx.AllTransactionScreen
import com.manoj.wallettracker.presentation.auth.BiometricAuthScreen
import com.manoj.wallettracker.presentation.graph.ExpenseGraphScreen
import com.manoj.wallettracker.presentation.home.HomeScreen
import com.manoj.wallettracker.presentation.home.vm.HomeViewModel
import com.manoj.wallettracker.presentation.splash.SplashScreen
import com.manoj.wallettracker.presentation.splash.vm.SplashViewModel

@Composable
fun MainNavHost() {
    InitializeHost()
}

@Composable
fun InitializeHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Routes.SPLASH
    ) {
        composable(route = Routes.SPLASH) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(route = Routes.HOME) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController,
                viewModel,
                {
                    navController.navigate(
                        Routes.ADD_EDIT_EXPENSE.route.replace(
                            "{${AppConstants.ARG_TRANSACTION_ID}}",
                            ""
                        )
                    )
                },
                { navController.navigate(Routes.GRAPH) },
                { navController.navigate(Routes.ALL_TRANSACTIONS) })
        }
        composable(
            route = Routes.ADD_EDIT_EXPENSE.route, arguments = Routes.ADD_EDIT_EXPENSE.arguments
        ) {
            val viewModel = hiltViewModel<TransactionDetailViewModel>()
            AddEditTransactionScreen(viewModel = viewModel,
                onClosePage = { navController.navigateUp() },
                onCancelClick = { navController.navigateUp() },
                onCategorySectionClick = { navController.navigate(Routes.CATEGORY) })
        }

        composable(route = Routes.CATEGORY) {
            val viewModel = hiltViewModel<TransactionDetailViewModel>()
            CategorySelectionScreen(viewModel, onClick = { navController.navigateUp() })
        }

        composable(route = Routes.GRAPH) {
            val viewModel = hiltViewModel<HomeViewModel>()
            ExpenseGraphScreen(viewModel)
        }

        composable(route = Routes.BIOMETRIC) {
            BiometricAuthScreen(navController)
        }

        composable(route = Routes.ALL_TRANSACTIONS) {
            val viewModel = hiltViewModel<HomeViewModel>()
            AllTransactionScreen(viewModel, onTransactionItemClick = {
                navController.navigate(
                    Routes.ADD_EDIT_EXPENSE.route.replace(
                        "{${AppConstants.ARG_TRANSACTION_ID}}", it.toString()
                    )
                )
            })
        }
    }
}