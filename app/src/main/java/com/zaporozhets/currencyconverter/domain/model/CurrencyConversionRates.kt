package com.zaporozhets.currencyconverter.domain.model

data class CurrencyConversionRates(
    val baseCurrency: String,
    val rates: Map<String, Double>,
)