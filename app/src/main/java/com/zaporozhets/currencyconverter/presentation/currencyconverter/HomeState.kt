package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zaporozhets.currencyconverter.domain.model.UiState

data class HomeState(
    var uiState: MutableState<UiState> = mutableStateOf(UiState.NoData),
    var amountToConvert: MutableState<String> = mutableStateOf(""),
    var validationError: MutableState<String> = mutableStateOf(""),
    val baseCurrency: MutableState<String> = mutableStateOf("Base currency"),
    val targetCurrency: MutableState<String> = mutableStateOf("Target currency"),
)