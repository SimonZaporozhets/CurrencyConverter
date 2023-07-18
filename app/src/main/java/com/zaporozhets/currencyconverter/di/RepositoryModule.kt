package com.zaporozhets.currencyconverter.di

import android.content.Context
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.remote.api.ExchangeRatesApi
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideCurrencyRepository(
        exchangeRatesApi: ExchangeRatesApi,
        conversionRatesDao: ConversionRatesDao,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(exchangeRatesApi, conversionRatesDao)
    }

    @Singleton
    @Provides
    fun provideErrorMessageRepository(@ApplicationContext context: Context): ErrorMessageRepository {
        return ErrorMessageRepositoryImpl(context.resources)
    }
}