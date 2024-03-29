package com.zaporozhets.currencyconverter.utils

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object CurrencySelectionScreen : Screen("currency_selection_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
