package com.zaporozhets.currencyconverter.di

import android.content.Context
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.zaporozhets.currencyconverter.domain.usecases.GetAllCurrenciesUseCase
import com.zaporozhets.currencyconverter.domain.usecases.ValidationAmountUseCase
import com.zaporozhets.currencyconverter.utils.ConnectivityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConvertCurrencyUseCase(
        currencyRepository: CurrencyRepository,
        errorMessageRepository: ErrorMessageRepository
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(currencyRepository, errorMessageRepository)
    }

    @Singleton
    @Provides
    fun provideValidationAmountUseCase(
        errorMessageRepository: ErrorMessageRepository,
    ): ValidationAmountUseCase {
        return ValidationAmountUseCase(errorMessageRepository)
    }


    @Singleton
    @Provides
    fun provideGetAllCurrenciesUseCase(
        currencyRepository: CurrencyRepository,
        errorMessageRepository: ErrorMessageRepository
    ): GetAllCurrenciesUseCase {
        return GetAllCurrenciesUseCase(currencyRepository, errorMessageRepository)
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker {
        return ConnectivityChecker(context)
    }


}