package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.zaporozhets.currencyconverter.domain.model.UiState

data class HomeState(
    var uiState: MutableState<UiState> = mutableStateOf(UiState.NoData),
    var currencies: SnapshotStateList<String> = mutableStateListOf(),
    var amountToConvert: MutableState<String> = mutableStateOf(""),
    var validationError: MutableState<String> = mutableStateOf(""),
    val baseCurrency: MutableState<String> = mutableStateOf("USD"),
    val targetCurrency: MutableState<String> = mutableStateOf("EUR"),
)