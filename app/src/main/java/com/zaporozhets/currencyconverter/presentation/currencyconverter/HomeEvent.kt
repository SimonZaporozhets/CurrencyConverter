package com.zaporozhets.currencyconverter.presentation.currencyconverter

sealed class HomeEvent {
    object ConvertCurrency : HomeEvent()
    data class ChangeAmount(val amount: String) : HomeEvent()
    data class UpdateBaseCurrency(val currency: String) : HomeEvent()
    data class UpdateTargetCurrency(val currency: String) : HomeEvent()
}
