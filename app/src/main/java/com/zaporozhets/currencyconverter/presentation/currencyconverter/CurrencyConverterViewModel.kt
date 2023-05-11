package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaporozhets.currencyconverter.domain.model.ConversionResult
import com.zaporozhets.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : ViewModel() {

    private val _conversionResult = mutableStateOf<ConversionResult>(ConversionResult.NoData)
    val conversionResult: State<ConversionResult> = _conversionResult

    fun convertCurrency(amount: Double, baseCurrency: String, targetCurrency: String) {
        viewModelScope.launch {
            try {
                val result = convertCurrencyUseCase.execute(
                    baseCurrency,
                    targetCurrency,
                    amount,
                )
                _conversionResult.value = ConversionResult.Success(result)
            } catch (e: Exception) {
                _conversionResult.value = ConversionResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}