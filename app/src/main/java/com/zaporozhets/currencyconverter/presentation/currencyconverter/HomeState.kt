package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HomeState(
    val uiState: MutableState<HomeUiState> = mutableStateOf(HomeUiState.NoData),
    val amountToConvert: MutableState<String> = mutableStateOf(""),
    val validationError: MutableState<String> = mutableStateOf(""),
    val baseCurrency: MutableState<String> = mutableStateOf("Base currency"),
    val targetCurrency: MutableState<String> = mutableStateOf("Target currency"),
)