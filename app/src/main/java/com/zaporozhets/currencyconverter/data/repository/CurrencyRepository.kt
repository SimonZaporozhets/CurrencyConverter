package com.zaporozhets.currencyconverter.data.repository

import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates

interface CurrencyRepository {

    suspend fun getCurrencyConversionRates(baseCurrency: String) : CurrencyConversionRates

}