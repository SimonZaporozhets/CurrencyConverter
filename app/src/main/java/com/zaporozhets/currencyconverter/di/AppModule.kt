package com.zaporozhets.currencyconverter.di

import android.content.Context
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.domain.usecase.ConvertCurrencyUseCase
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
    fun provideConvertCurrencyUseCase(currencyRepository: CurrencyRepository): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(currencyRepository)
    }

    @Singleton
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker {
        return ConnectivityChecker(context)
    }


}