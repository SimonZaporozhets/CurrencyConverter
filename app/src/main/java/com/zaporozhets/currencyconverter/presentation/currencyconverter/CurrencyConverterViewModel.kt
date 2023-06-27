package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.domain.model.ConversionResult
import com.zaporozhets.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.zaporozhets.currencyconverter.domain.usecase.GetAllCurrenciesUseCase
import com.zaporozhets.currencyconverter.utils.ConnectivityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val connectivityChecker: ConnectivityChecker,
) : ViewModel() {

    private val _conversionResult = mutableStateOf<ConversionResult>(ConversionResult.NoData)
    val conversionResult: State<ConversionResult> = _conversionResult

    private var _currenciesList = mutableStateListOf<String>()
    val currenciesList: List<String> = _currenciesList

    private val _isOnline = MutableStateFlow(connectivityChecker.isOnline())
    val isOnline: StateFlow<Boolean> = _isOnline

    init {
        viewModelScope.launch {
            connectivityChecker.registerNetworkCallback { isOnline ->
                _isOnline.value = isOnline
            }
        }
        getAllCurrencies()
    }

    private fun getAllCurrencies() {
        viewModelScope.launch {
            try {
                _currenciesList.addAll(getAllCurrenciesUseCase.execute())
            } catch (e: Exception) {
                when (e) {
                    is SocketTimeoutException, is UnknownHostException ->
                        _conversionResult.value =
                            ConversionResult.Error("Network error occurred")

                    else ->
                        _conversionResult.value =
                            ConversionResult.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    fun convertCurrency(amount: Double, baseCurrency: String, targetCurrency: String) {
        viewModelScope.launch {
            _conversionResult.value = ConversionResult.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    convertCurrencyUseCase.execute(
                        baseCurrency,
                        targetCurrency,
                        amount,
                    )
                }
                _conversionResult.value = ConversionResult.Success(result)
            } catch (e: Exception) {
                when (e) {
                    is SocketTimeoutException, is UnknownHostException ->
                        _conversionResult.value =
                            ConversionResult.Error("Network error occurred")

                    else ->
                        _conversionResult.value =
                            ConversionResult.Error(e.message ?: "Unknown error")
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        connectivityChecker.unregisterNetworkCallback()
    }
}