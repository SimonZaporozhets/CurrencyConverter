package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.data.repository.ErrorMessageRepository
import com.zaporozhets.currencyconverter.domain.Result
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.usecases.GetAllCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CurrencySelectionViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val errorMessageRepository: ErrorMessageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencySelectionState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private var allCurrencies = listOf<Currency>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        state.value.uiState.value = when (exception) {
            is SocketTimeoutException, is UnknownHostException -> {
                CurrencySelectionUiState.Error(errorMessageRepository.getNetworkErrorOccurredMessage())
            }

            else -> {
                CurrencySelectionUiState.Error(
                    exception.message ?: errorMessageRepository.getUnknownError()
                )
            }
        }
    }

    init {
        getAllCurrencies()
        filterCurrencies()
    }

    private fun getAllCurrencies() {
        state.value.uiState.value = CurrencySelectionUiState.Loading
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            when (val allCurrenciesResult = getAllCurrenciesUseCase.execute(Unit)) {
                is Result.Error -> {
                    state.value.uiState.value =
                        CurrencySelectionUiState.Error(allCurrenciesResult.message)
                }

                is Result.Success -> {
                    allCurrencies = allCurrenciesResult.value
                    state.value.currencies.value = allCurrencies
                    state.value.uiState.value = CurrencySelectionUiState.Success(allCurrencies)
                }
            }
        }
    }

    private fun filterCurrencies() {
        viewModelScope.launch(exceptionHandler) {
            state.value.searchQuery
                .combine(state.value.currencies) { query, _ ->
                    if (query.isBlank()) {
                        allCurrencies
                    } else {
                        allCurrencies.filter {
                            it.doesMatchSearchQuery(query)
                        }
                    }
                }
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