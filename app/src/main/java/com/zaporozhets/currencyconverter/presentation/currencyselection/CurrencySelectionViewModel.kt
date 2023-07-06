package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.domain.usecase.GetAllCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CurrencySelectionViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencySelectionState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

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
        getAllCurrencies()
    }

    private fun getAllCurrencies() {
        viewModelScope.launch(exceptionHandler) {
            state.value.uiState.value = UiState.Loading
            state.value.currencies.clear()
            state.value.currencies.addAll(getAllCurrenciesUseCase.execute(Unit))
            state.value.uiState.value = UiState.NoData
        }
    }

    fun onEvent(event: CurrencySelectionEvent) {
        when (event) {
            is CurrencySelectionEvent.CurrencySelected -> {
                viewModelScope.launch { _navigationEvent.emit(event.currencyName) }
            }

            is CurrencySelectionEvent.SearchQueryChanged -> {

            }
        }
    }
}