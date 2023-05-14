package com.zaporozhets.currencyconverter.data.repository

import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.local.entities.ConversionRates
import com.zaporozhets.currencyconverter.data.remote.api.ExchangeRatesApi
import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates

class CurrencyRepositoryImpl(
    private val exchangeRatesApi: ExchangeRatesApi,
    private val conversionRateDao: ConversionRatesDao,
) : CurrencyRepository {

    override suspend fun getCurrencyConversionRates(baseCurrency: String): CurrencyConversionRates {
        val cachedRates = conversionRateDao.getConversionRates(baseCurrency)
        return if (cachedRates != null && !isStale(cachedRates)) {
            CurrencyConversionRates(baseCurrency, cachedRates.rates)
        } else {
            try {
                val ratesFromApi = exchangeRatesApi.getCurrencyConversionRates(baseCurrency)
                val conversionRates =
                    ConversionRates(baseCurrency, ratesFromApi.rates, System.currentTimeMillis())
                conversionRateDao.insertConversionRates(conversionRates)
                ratesFromApi
            } catch (e: Exception) {
                if (cachedRates != null) {
                    CurrencyConversionRates(baseCurrency, cachedRates.rates)
                } else {
                    throw e
                }
            }
        }
    }

    private fun isStale(conversionRates: ConversionRates): Boolean {
        val oneHourInMillis = 60 * 60 * 1000
        return (System.currentTimeMillis() - conversionRates.timestamp) > oneHourInMillis;
    }

}