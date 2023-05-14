package com.zaporozhets.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.local.entities.ConversionRates

@Database(entities = [ConversionRates::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionRatesDao(): ConversionRatesDao
}