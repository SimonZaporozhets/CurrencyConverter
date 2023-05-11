package com.zaporozhets.currencyconverter.di

import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConvertCurrencyUseCase(currencyRepository: CurrencyRepository) : ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(currencyRepository)
    }


}