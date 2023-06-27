package com.zaporozhets.currencyconverter.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symbols")
data class CurrencySymbol(
    @PrimaryKey val symbol: String,
    val currencyName: String,
    val timestamp: Long
)

