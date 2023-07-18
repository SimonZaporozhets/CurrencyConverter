package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.Result
import com.zaporozhets.currencyconverter.domain.model.ConvertCurrencyParams
import com.zaporozhets.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import com.zaporozhets.currencyconverter.domain.usecases.ValidationAmountUseCase
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
    private val validationAmountUseCase: ValidationAmountUseCase,
    private val errorMessageRepository: ErrorMessageRepository,
    private val connectivityChecker: ConnectivityChecker,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _isOnline = MutableStateFlow(connectivityChecker.isOnline())
    val isOnline: StateFlow<Boolean> = _isOnline


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        state.value.uiState.value = when (exception) {
            is SocketTimeoutException, is UnknownHostException -> {
                HomeUiState.Error(errorMessageRepository.getNetworkErrorOccurredMessage())
            }

            else -> {
                HomeUiState.Error(
                    exception.message ?: errorMessageRepository.getUnknownError()
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            connectivityChecker.registerNetworkCallback { isOnline ->
                _isOnline.value = isOnline
            }
        }
    }

    private fun convertCurrency() {
        val amountValue = state.value.amountToConvert.value

        viewModelScope.launch(exceptionHandler) {
            when (val validationResult = validationAmountUseCase.execute(amountValue)) {
                is Result.Error -> state.value.validationError.value =
                    validationResult.message

                is Result.Success -> {
                    state.value.uiState.value = HomeUiState.Loading
                    val conversionResult = withContext(Dispatchers.IO) {
                        convertCurrencyUseCase.execute(
                            ConvertCurrencyParams(
                                state.value.baseCurrency.value,
                                state.value.targetCurrency.value,
                                validationResult.value
                            )
                        )
                    }
                    when (conversionResult) {
                        is Result.Error -> state.value.uiState.value =
                            HomeUiState.Error(conversionResult.message)

                        is Result.Success -> state.value.uiState.value =
                            HomeUiState.Success(conversionResult.value)

                    }
                }

            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ConvertCurrency -> {
                convertCurrency()
            }

            is HomeEvent.ChangeAmount -> {
                state.value.amountToConvert.value = event.amount
                state.value.validationError.value = ""
            }

            is HomeEvent.UpdateBaseCurrency -> state.value.baseCurrency.value = event.currency
            is HomeEvent.UpdateTargetCurrency -> state.value.targetCurrency.value = event.currency
        }
    }


    override fun onCleared() {
        super.onCleared()
        connectivityChecker.unregisterNetworkCallback()
    }
}