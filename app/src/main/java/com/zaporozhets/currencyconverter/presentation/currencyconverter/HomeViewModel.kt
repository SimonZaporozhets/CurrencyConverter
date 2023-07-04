package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.zaporozhets.currencyconverter.domain.usecase.GetAllCurrenciesUseCase
import com.zaporozhets.currencyconverter.utils.ConnectivityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val connectivityChecker: ConnectivityChecker,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _isOnline = MutableStateFlow(connectivityChecker.isOnline())
    val isOnline: StateFlow<Boolean> = _isOnline


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        state.value.uiState.value = when (exception) {
            is SocketTimeoutException, is UnknownHostException -> {
                UiState.Error("Network error occurred")
            }

            else -> {
                UiState.Error(exception.message ?: "Unknown error")
            }
        }
    }

    init {
        viewModelScope.launch {
            connectivityChecker.registerNetworkCallback { isOnline ->
                _isOnline.value = isOnline
            }
        }
        getAllCurrencies()
    }

    private fun getAllCurrencies() {
        viewModelScope.launch(exceptionHandler) {
            state.value.uiState.value = UiState.Loading
            state.value.currencies.clear()
            state.value.currencies.addAll(getAllCurrenciesUseCase.execute())
            state.value.uiState.value = UiState.NoData
        }
    }

    private fun convertCurrency() {
        val amountValue = state.value.amountToConvert.value
        if (amountValue.isNotBlank() && amountValue.toDouble() > 0) {
            viewModelScope.launch(exceptionHandler) {
                state.value.uiState.value = UiState.Loading
                val result = withContext(Dispatchers.IO) {
                    convertCurrencyUseCase.execute(
                        state.value.baseCurrency.value,
                        state.value.targetCurrency.value,
                        amountValue.toDouble()
                    )
                }
                state.value.uiState.value = UiState.ConversionSuccess(result)
            }
            state.value.validationError.value = ""
        } else {
            state.value.validationError.value = "Invalid Amount"
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ConvertCurrency -> {
                convertCurrency()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        connectivityChecker.unregisterNetworkCallback()
    }
}