package com.zaporozhets.currencyconverter.domain.usecase

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.domain.model.ConvertCurrencyParams

class ConvertCurrencyUseCase(
    private val currencyRepository: CurrencyRepository,
) : UseCase<ConvertCurrencyParams, Double> {
    override suspend fun execute(params: ConvertCurrencyParams): Double {
        return currencyRepository.convertCurrency(
            params.amount,
            params.baseCurrency,
            params.targetCurrency
        )
    }
}