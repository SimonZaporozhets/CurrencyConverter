package com.zaporozhets.currencyconverter.di

import android.content.Context
import com.zaporozhets.currencyconverter.data.local.AppDatabase
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): ConversionRatesDao {
        return appDatabase.conversionRatesDao()
    }

}