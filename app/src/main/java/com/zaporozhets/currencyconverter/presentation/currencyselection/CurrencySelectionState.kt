package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zaporozhets.currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.MutableStateFlow

data class CurrencySelectionState(
    val uiState: MutableState<CurrencySelectionUiState> = mutableStateOf(
        CurrencySelectionUiState.NoData
    ),
    val searchQuery: MutableStateFlow<String> = MutableStateFlow(""),
    val currencies: MutableStateFlow<List<Currency>> = MutableStateFlow(listOf()),
)
