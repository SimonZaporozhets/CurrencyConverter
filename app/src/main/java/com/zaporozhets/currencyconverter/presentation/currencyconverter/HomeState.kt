package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zaporozhets.currencyconverter.domain.model.UiState

data class HomeState(
    val uiState: MutableState<UiState> = mutableStateOf(UiState.NoData),
    val amountToConvert: MutableState<String> = mutableStateOf(""),
    val validationError: MutableState<String> = mutableStateOf(""),
    val baseCurrency: MutableState<String> = mutableStateOf("Base currency"),
    val targetCurrency: MutableState<String> = mutableStateOf("Target currency"),
)