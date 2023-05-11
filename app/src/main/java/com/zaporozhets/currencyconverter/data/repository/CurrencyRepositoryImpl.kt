package com.zaporozhets.currencyconverter.data.repository

import com.zaporozhets.currencyconverter.data.remote.api.ExchangeRatesApi
import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates

class CurrencyRepositoryImpl (
    private val exchangeRatesApi: ExchangeRatesApi
        ) : CurrencyRepository {

    override suspend fun getCurrencyConversionRates(baseCurrency: String): CurrencyConversionRates {
        return exchangeRatesApi.getCurrencyConversionRates(baseCurrency)
    }

}