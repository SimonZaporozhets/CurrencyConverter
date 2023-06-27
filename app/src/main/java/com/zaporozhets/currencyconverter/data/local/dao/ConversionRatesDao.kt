package com.zaporozhets.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.zaporozhets.currencyconverter.data.local.entities.CurrencyRate
import com.zaporozhets.currencyconverter.data.local.entities.LatestCurrencyRates
import com.zaporozhets.currencyconverter.data.local.entities.CurrencySymbol

@Dao
interface ConversionRatesDao {

    @Query("SELECT * FROM currencyRates WHERE baseCurrency = :baseCurrency")
    suspend fun getConversionRates(baseCurrency: String): LatestCurrencyRates?

    @Query("SELECT * FROM symbols")
    suspend fun getAllCurrencies(): List<CurrencySymbol>?

    @Query("SELECT * FROM currencyRate WHERE `from` = :from AND `to` = :to")
    suspend fun getConversionRate(from: String, to: String): CurrencyRate?

    @Insert(onConflict = REPLACE)
    suspend fun insertConversionRates(conversionRates: LatestCurrencyRates)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllCurrencies(currencySymbols: List<CurrencySymbol>)

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrencyRate(currencyRate: CurrencyRate)

}