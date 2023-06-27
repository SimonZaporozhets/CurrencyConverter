package com.zaporozhets.currencyconverter.domain.model

data class CurrenciesResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)