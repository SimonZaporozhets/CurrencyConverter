package com.zaporozhets.currencyconverter.domain.model

sealed class ConversionResult {
    object NoData : ConversionResult()
    object Loading: ConversionResult()
    data class Success(val value: Double) : ConversionResult()
    data class Error(val message: String) : ConversionResult()
}