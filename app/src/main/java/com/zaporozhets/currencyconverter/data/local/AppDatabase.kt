package com.zaporozhets.currencyconverter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaporozhets.currencyconverter.data.local.converters.Converters
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.local.entities.CurrencyRate
import com.zaporozhets.currencyconverter.data.local.entities.CurrencySymbol
import com.zaporozhets.currencyconverter.data.local.entities.LatestCurrencyRates

@Database(
    entities = [LatestCurrencyRates::class, CurrencySymbol::class, CurrencyRate::class],
    version = 5
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionRatesDao(): ConversionRatesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}