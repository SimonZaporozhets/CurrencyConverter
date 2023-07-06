package com.zaporozhets.currencyconverter.domain.usecase

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.domain.model.Currency

class GetAllCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) : UseCase<Unit, List<Currency>> {
    override suspend fun execute(params: Unit): List<Currency> {
        return currencyRepository.getAllCurrencies()
    }
}