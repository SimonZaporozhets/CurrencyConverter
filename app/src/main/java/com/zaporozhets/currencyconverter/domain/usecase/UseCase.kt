package com.zaporozhets.currencyconverter.domain.usecase

interface UseCase<in Params, out Result> {
    suspend fun execute(params: Params): Result
}