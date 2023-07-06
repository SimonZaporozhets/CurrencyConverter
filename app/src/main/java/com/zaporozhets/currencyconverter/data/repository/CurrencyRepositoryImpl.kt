package com.zaporozhets.currencyconverter.data.repository

import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.local.entities.CurrencyRate
import com.zaporozhets.currencyconverter.data.local.entities.CurrencySymbol
import com.zaporozhets.currencyconverter.data.local.entities.LatestCurrencyRates
import com.zaporozhets.currencyconverter.data.remote.api.ExchangeRatesApi
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates

class CurrencyRepositoryImpl(
    private val exchangeRatesApi: ExchangeRatesApi,
    private val conversionRateDao: ConversionRatesDao,
) : CurrencyRepository {

    override suspend fun getConversionRates(baseCurrency: String): CurrencyConversionRates {
        val cachedRates = conversionRateDao.getConversionRates(baseCurrency)
        return if (cachedRates != null && !isStale(cachedRates.timestamp)) {
            CurrencyConversionRates(baseCurrency, cachedRates.rates)
        } else {
            try {
                val ratesFromApi = exchangeRatesApi.getCurrencyConversionRates(baseCurrency)
                val conversionRates =
                    LatestCurrencyRates(
                        baseCurrency,
                        ratesFromApi.rates,
                        System.currentTimeMillis()
                    )
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

    override suspend fun convertCurrency(amount: Double, from: String, to: String): Double {
        val cachedRate = conversionRateDao.getConversionRate(from, to)
        return if (cachedRate != null && !isStale(cachedRate.timestamp)) {
            cachedRate.rate * amount
        } else {
            try {
                val rateFromApi = exchangeRatesApi.convertCurrency(amount.toString(), from, to)
                conversionRateDao.insertCurrencyRate(
                    CurrencyRate(
                        rateFromApi.query.from,
                        rateFromApi.query.to,
                        rateFromApi.info.rate,
                        System.currentTimeMillis()
                    )
                )
                rateFromApi.result
            } catch (e: Exception) {
                if (cachedRate != null) {
                    cachedRate.rate * amount
                } else {
                    throw e
                }
            }
        }
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        val cachedSymbols = conversionRateDao.getAllCurrencies()
        return if (!cachedSymbols.isNullOrEmpty() && !isStale(cachedSymbols[0].timestamp)) {
            cachedSymbols.map { Currency(it.symbol, it.currencyName) }
        } else {
            try {
                val symbolsFromApi = exchangeRatesApi.getAllCurrencies()
                val symbols = symbolsFromApi.symbols.map {
                    CurrencySymbol(it.key, it.value, System.currentTimeMillis())
                }
                conversionRateDao.insertAllCurrencies(symbols)
                symbolsFromApi.symbols.map { Currency(it.key, it.value) }
            } catch (e: Exception) {
                if (!cachedSymbols.isNullOrEmpty()) {
                    cachedSymbols.map { Currency(it.symbol, it.currencyName) }
                } else {
                    throw e
                }
            }
        }
    }

    private fun isStale(timestamp: Long): Boolean {
        val oneHourInMillis = 60 * 60 * 1000
        return (System.currentTimeMillis() - timestamp) > oneHourInMillis
    }

}