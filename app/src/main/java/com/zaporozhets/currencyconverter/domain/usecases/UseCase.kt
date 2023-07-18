package com.zaporozhets.currencyconverter.domain.usecases

import com.zaporozhets.currencyconverter.domain.Result

interface UseCase<in Params, out T> {
    suspend fun execute(params: Params): Result<T>
}