package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.domain.usecase.GetAllCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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

    private var allCurrencies = listOf<Currency>()

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
        filterCurrencies()
    }

    private fun getAllCurrencies() {
        viewModelScope.launch(exceptionHandler) {
            state.value.uiState.value = UiState.Loading
            allCurrencies = getAllCurrenciesUseCase.execute(Unit)
            state.value.currencies.value = allCurrencies
            state.value.uiState.value = UiState.NoData
        }
    }

    private fun filterCurrencies() {
        viewModelScope.launch(exceptionHandler) {
            state.value.searchQuery
                .onEach { state.value.isSearching.update { true } }
                .combine(state.value.currencies) { query, _ ->
                    if (query.isBlank()) {
                        allCurrencies
                    } else {
                        allCurrencies.filter {
                            it.doesMatchSearchQuery(query)
                        }
                    }
                }
                .onEach { state.value.isSearching.update { false } }
                .collectLatest { filteredCurrencies ->
                    state.value.currencies.value = filteredCurrencies
                }
        }
    }

    fun onEvent(event: CurrencySelectionEvent) {
        when (event) {
            is CurrencySelectionEvent.CurrencySelected -> {
                viewModelScope.launch { _navigationEvent.emit(event.currencyName) }
            }

            is CurrencySelectionEvent.SearchQueryChanged -> {
                state.value.searchQuery.value = event.newQuery
            }
        }
    }
}