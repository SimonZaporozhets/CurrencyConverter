package com.zaporozhets.currencyconverter.domain.usecase

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository

class GetAllCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun execute(): List<String> {
        return currencyRepository.getAllCurrencies()
    }

}