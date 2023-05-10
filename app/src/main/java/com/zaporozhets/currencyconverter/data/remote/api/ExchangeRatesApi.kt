package com.zaporozhets.currencyconverter.data.remote.api

import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {


    @GET("latest")
    suspend fun getCurrencyConversionRates(
        @Query("base") baseCurrency: String,
    ): CurrencyConversionRates


}