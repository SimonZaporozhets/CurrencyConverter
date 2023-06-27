package com.zaporozhets.currencyconverter.data.remote.api

import com.zaporozhets.currencyconverter.domain.model.ConvertCurrencyResponse
import com.zaporozhets.currencyconverter.domain.model.CurrenciesResponse
import com.zaporozhets.currencyconverter.domain.model.CurrencyConversionRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {


    @GET("latest")
    suspend fun getCurrencyConversionRates(
        @Query("base") baseCurrency: String,
    ): CurrencyConversionRates

    @GET("convert")
    suspend fun convertCurrency(
        @Query("amount") amount: String,
        @Query("from") from: String,
        @Query("to") to: String,
    ): ConvertCurrencyResponse

    @GET("symbols")
    suspend fun getAllCurrencies(): CurrenciesResponse

}