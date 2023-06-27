package com.zaporozhets.currencyconverter.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencyRates")
data class LatestCurrencyRates(
    @PrimaryKey val baseCurrency: String,
    val rates: Map<String, Double>,
    val timestamp: Long,
)