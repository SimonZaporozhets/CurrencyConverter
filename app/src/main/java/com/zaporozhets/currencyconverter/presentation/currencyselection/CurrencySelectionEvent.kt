package com.zaporozhets.currencyconverter.presentation.currencyselection

sealed class CurrencySelectionEvent {
    data class SearchQueryChanged(val newQuery: String) : CurrencySelectionEvent()
    data class CurrencySelected(
        val currencyName: String,
        val currencyFor: String,
    ) : CurrencySelectionEvent()
}
