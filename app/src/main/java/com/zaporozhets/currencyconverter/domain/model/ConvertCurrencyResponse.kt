package com.zaporozhets.currencyconverter.domain.model

data class ConvertCurrencyResponse(
    val date: String,
    val info: ConvertInfo,
    val query: ConvertQuery,
    val result: Double,
    val success: Boolean
)