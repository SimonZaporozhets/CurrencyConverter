package com.zaporozhets.currencyconverter.domain.usecase

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository

class ConvertCurrencyUseCase(
    private val currencyRepository: CurrencyRepository,
) {
    suspend fun execute(
        baseCurrency: String,
        targetCurrency: String,
        amount: Double,
    ): Double {
        val exchangeRatesApi = currencyRepository.getCurrencyConversionRates(baseCurrency)
        val rate = exchangeRatesApi.rates[targetCurrency] ?: 0.0
        return amount * rate
    }
}