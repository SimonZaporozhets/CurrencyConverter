package com.zaporozhets.currencyconverter.presentation.currencyselection

import com.zaporozhets.currencyconverter.domain.model.Currency

sealed class CurrencySelectionUiState {
    object Loading : CurrencySelectionUiState()
    object Searching : CurrencySelectionUiState()
    object NoData : CurrencySelectionUiState()
    data class Success(val currencies: List<Currency>) : CurrencySelectionUiState()
    data class Error(val message: String) : CurrencySelectionUiState()
}