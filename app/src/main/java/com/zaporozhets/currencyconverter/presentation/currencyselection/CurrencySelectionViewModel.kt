package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencySelectionViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(CurrencySelectionState())
    val state = _state.asStateFlow()

    fun onEvent(event: CurrencySelectionEvent) {
        when(event) {
            is CurrencySelectionEvent.CurrencySelected -> {

            }
            is CurrencySelectionEvent.SearchQueryChanged -> {

            }
        }
    }
}