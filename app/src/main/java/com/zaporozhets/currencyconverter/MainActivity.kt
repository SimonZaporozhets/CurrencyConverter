package com.zaporozhets.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zaporozhets.currencyconverter.presentation.currencyconverter.HomeScreen
import com.zaporozhets.currencyconverter.presentation.currencyconverter.HomeViewModel
import com.zaporozhets.currencyconverter.presentation.currencyselection.CurrencySelectionScreen
import com.zaporozhets.currencyconverter.presentation.currencyselection.CurrencySelectionViewModel
import com.zaporozhets.currencyconverter.presentation.ui.theme.CurrencyConverterTheme
import com.zaporozhets.currencyconverter.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) { entry ->
                            val viewModel = hiltViewModel<HomeViewModel>()
                            val selectedCurrency =
                                entry.savedStateHandle.get<String>("currency_name") ?: ""
                            val currencyFor =
                                entry.savedStateHandle.get<String>("currency_for") ?: ""
                            HomeScreen(
                                state = viewModel.state.collectAsState().value,
                                onEvent = viewModel::onEvent,
                                navController,
                                selectedCurrency,
                                currencyFor
                            )
                        }
                        composable(
                            route = Screen.CurrencySelectionScreen.route + "/{currency_for}",
                            arguments = listOf(
                                navArgument("currency_for") {
                                    type = NavType.StringType
                                    defaultValue = "base"
                                    nullable = false
                                }
                            )
                        ) { entry ->
                            val viewModel = hiltViewModel<CurrencySelectionViewModel>()
                            CurrencySelectionScreen(
                                state = viewModel.state.collectAsState().value,
                                navigationEvent = viewModel.navigationEvent,
                                onEvent = viewModel::onEvent,
                                navController = navController,
                                currencyFor = entry.arguments?.getString("currency_for") ?: "base"
                            )
                        }
                    }
                }
            }
        }
    }
}
