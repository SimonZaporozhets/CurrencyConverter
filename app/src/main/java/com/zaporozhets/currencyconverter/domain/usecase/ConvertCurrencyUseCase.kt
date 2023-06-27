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
        return currencyRepository.convertCurrency(amount, baseCurrency, targetCurrency)
    }
}