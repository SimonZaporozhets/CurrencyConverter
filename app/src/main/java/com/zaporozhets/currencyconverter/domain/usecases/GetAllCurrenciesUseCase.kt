package com.zaporozhets.currencyconverter.domain.usecases

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.Result
import com.zaporozhets.currencyconverter.domain.model.Currency

class GetAllCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository,
    private val errorMessageRepository: ErrorMessageRepository,
) : UseCase<Unit, List<Currency>> {
    override suspend fun execute(params: Unit): Result<List<Currency>> {
        return try {
            Result.Success(currencyRepository.getAllCurrencies())
        } catch (e: Exception) {
            Result.Error(e.message ?: errorMessageRepository.getUnknownError())
        }
    }
}