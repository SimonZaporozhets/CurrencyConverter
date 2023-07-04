package com.zaporozhets.currencyconverter.domain.model

sealed class UiState {
    object NoData : UiState()
    object Loading: UiState()
    data class ConversionSuccess(val value: Double) : UiState()
    data class Error(val message: String) : UiState()
}