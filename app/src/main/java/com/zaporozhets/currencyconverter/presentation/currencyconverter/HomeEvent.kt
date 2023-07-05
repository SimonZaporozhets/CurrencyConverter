package com.zaporozhets.currencyconverter.presentation.currencyconverter

sealed class HomeEvent {
    object ConvertCurrency : HomeEvent()
    data class ChangeAmount(val amount: String) : HomeEvent()
}
