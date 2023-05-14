package com.zaporozhets.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaporozhets.currencyconverter.data.local.entities.ConversionRates

@Dao
interface ConversionRatesDao {

    @Query("SELECT * FROM conversionrates WHERE baseCurrency = :baseCurrency")
    suspend fun getConversionRates(baseCurrency: String): ConversionRates?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRates(conversionRates: ConversionRates)

}