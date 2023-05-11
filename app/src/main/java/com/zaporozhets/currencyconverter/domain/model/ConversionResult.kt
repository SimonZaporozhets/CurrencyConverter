package com.zaporozhets.currencyconverter.domain.model

sealed class ConversionResult {
    object NoData : ConversionResult()
    data class Success(val value: Double) : ConversionResult()
    data class Error(val message: String) : ConversionResult()
}