package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.UiState

data class CurrencySelectionState(
    var uiState: MutableState<UiState> = mutableStateOf(UiState.NoData),
    val searchQuery: MutableState<String> = mutableStateOf(""),
    val currencies: SnapshotStateList<Currency> = mutableStateListOf(),
)
