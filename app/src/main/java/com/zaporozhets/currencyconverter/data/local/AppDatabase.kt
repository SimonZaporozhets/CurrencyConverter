package com.zaporozhets.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaporozhets.currencyconverter.data.local.converters.Converters
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.local.entities.CurrencyRate
import com.zaporozhets.currencyconverter.data.local.entities.LatestCurrencyRates
import com.zaporozhets.currencyconverter.data.local.entities.CurrencySymbol

@Database(entities = [LatestCurrencyRates::class, CurrencySymbol::class, CurrencyRate::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionRatesDao(): ConversionRatesDao
}