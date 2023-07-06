package com.zaporozhets.currencyconverter.data.repository

import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates

interface CurrencyRepository {

    suspend fun getConversionRates(baseCurrency: String): CurrencyConversionRates

    suspend fun convertCurrency(amount: Double, from: String, to: String): Double

    suspend fun getAllCurrencies(): List<Currency>

}