package com.zaporozhets.currencyconverter.di

import com.zaporozhets.currencyconverter.BuildConfig
import com.zaporozhets.currencyconverter.data.local.dao.ConversionRatesDao
import com.zaporozhets.currencyconverter.data.remote.api.ExchangeRatesApi
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepository
import com.zaporozhets.currencyconverter.data.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_BASE_URL = "http://api.apilayer.com/exchangerates_data"
    private const val API_KEY_QUERY_PARAM = "access_key"

    @Singleton
    @Provides
    fun provideExchangeRatesApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.API_KEY)
                .build()
            val newRequest = request.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        exchangeRatesApi: ExchangeRatesApi,
        conversionRatesDao: ConversionRatesDao,
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(exchangeRatesApi, conversionRatesDao)
    }
}