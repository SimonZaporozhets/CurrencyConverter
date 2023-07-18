package com.zaporozhets.currencyconverter.domain.model

data class ConvertCurrencyParams(
    val baseCurrency: String,
    val targetCurrency: String,
    val amount: Double
)