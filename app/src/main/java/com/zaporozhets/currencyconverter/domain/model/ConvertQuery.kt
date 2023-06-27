package com.zaporozhets.currencyconverter.domain.model

data class ConvertQuery(
    val amount: Int,
    val from: String,
    val to: String
)