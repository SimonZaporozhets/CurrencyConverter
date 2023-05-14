package com.zaporozhets.currencyconverter.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversionRates(
    @PrimaryKey val baseCurrency: String,
    val rates: Map<String, Double>,
    val timestamp: Long,
)