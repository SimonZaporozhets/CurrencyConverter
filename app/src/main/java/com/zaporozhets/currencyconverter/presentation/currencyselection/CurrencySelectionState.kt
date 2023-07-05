package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class CurrencySelectionState(
    val searchQuery: MutableState<String> = mutableStateOf(""),
    val currencies: SnapshotStateList<String> = mutableStateListOf(),
)
