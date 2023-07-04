package com.zaporozhets.currencyconverter.domain.usecase

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository

class GetAllCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) : UseCase<Unit, List<String>> {
    override suspend fun execute(params: Unit): List<String> {
        return currencyRepository.getAllCurrencies()
    }
}