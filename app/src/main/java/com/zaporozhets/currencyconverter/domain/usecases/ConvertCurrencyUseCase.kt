package com.zaporozhets.currencyconverter.domain.usecases

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.Result
import com.zaporozhets.currencyconverter.domain.model.ConvertCurrencyParams

class ConvertCurrencyUseCase(
    private val currencyRepository: CurrencyRepository,
    private val errorMessageRepository: ErrorMessageRepository,
) : UseCase<ConvertCurrencyParams, Double> {
    override suspend fun execute(params: ConvertCurrencyParams): Result<Double> {
        return try {
            Result.Success(
                currencyRepository.convertCurrency(
                    params.amount,
                    params.baseCurrency,
                    params.targetCurrency
                )
            )
        } catch (e: Exception) {
            Result.Error(e.message ?: errorMessageRepository.getUnknownError())
        }
    }
}