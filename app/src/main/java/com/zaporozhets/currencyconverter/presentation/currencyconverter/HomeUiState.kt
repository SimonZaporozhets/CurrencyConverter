package com.zaporozhets.currencyconverter.presentation.currencyconverter

sealed class HomeUiState {
    object NoData : HomeUiState()
    object Loading: HomeUiState()
    data class Success(val value: Double) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}