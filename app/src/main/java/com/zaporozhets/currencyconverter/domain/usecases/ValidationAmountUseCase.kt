package com.zaporozhets.currencyconverter.domain.usecases

import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.Result
import com.zaporozhets.currencyconverter.utils.INTEGER
import com.zaporozhets.currencyconverter.utils.MAX_AMOUNT

class ValidationAmountUseCase(
    private val errorMessageRepository: ErrorMessageRepository,
) : UseCase<String, Double> {



    override suspend fun execute(params: String): Result<Double> {
        return when {
            params.isBlank() -> Result.Error(errorMessageRepository.getAmountCannotBeBlankError())
            !isInteger(params) -> Result.Error(errorMessageRepository.getInvalidAmountError())
            params.toInt() <= 0 -> Result.Error(errorMessageRepository.getAmountCannotBeBlankError())
            params.toInt() > MAX_AMOUNT -> Result.Error(errorMessageRepository.getAmountTooLargeError())
            else -> Result.Success(params.toDouble())
        }
    }

    private fun isInteger(input: String): Boolean {
        return input.matches(INTEGER.toRegex())
    }
}