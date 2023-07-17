package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow

data class CurrencySelectionState(
    val uiState: MutableState<UiState> = mutableStateOf(UiState.NoData),
    val searchQuery: MutableStateFlow<String> = MutableStateFlow(""),
    val isSearching: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val currencies: MutableStateFlow<List<Currency>> = MutableStateFlow(listOf()),
)
