package com.zaporozhets.currencyconverter.data.local.entities

import androidx.room.Entity

@Entity(primaryKeys = ["from", "to"], tableName = "currencyRate")
data class CurrencyRate(
    val from: String,
    val to: String,
    val rate: Double,
    val timestamp: Long
)
