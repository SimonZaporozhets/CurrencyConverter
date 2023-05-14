package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaporozhets.currencyconverter.R
import com.zaporozhets.currencyconverter.domain.model.ConversionResult
import com.zaporozhets.currencyconverter.presentation.currencyconverter.components.DropdownMenuCurrencySelector
import com.zaporozhets.currencyconverter.utils.currencies


@Composable
fun CurrencyConverterScreen(currencyViewModel: CurrencyConverterViewModel = viewModel()) {

    val conversionResult by currencyViewModel.conversionResult
    val amount = remember { mutableStateOf("") }
    val amountError = remember { mutableStateOf<String?>(null) }
    val baseCurrency = remember { mutableStateOf(currencies[0]) }
    val targetCurrency = remember { mutableStateOf(currencies[1]) }

    fun convertCurrency() {
        val amountValue = amount.value.toDoubleOrNull()
        if (amountValue != null && amountValue > 0) {
            currencyViewModel.convertCurrency(
                amountValue,
                baseCurrency.value,
                targetCurrency.value
            )
            amountError.value = null
        } else {
            amountError.value = "Invalid Amount"
        }
    }

    val isOnline by currencyViewModel.isOnline.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = amount.value,
            onValueChange = {
                amount.value = it
                amountError.value = null
            },
            label = { Text(stringResource(R.string.amount)) },
            isError = amountError.value != null
        )

        Spacer(modifier = Modifier.height(8.dp))

        amountError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            DropdownMenuCurrencySelector(selectedCurrency = baseCurrency,
                label = stringResource(R.string.base_currency))
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenuCurrencySelector(selectedCurrency = targetCurrency,
                label = stringResource(R.string.target_currency))

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { convertCurrency() }) {
            Text(stringResource(R.string.convert))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ConversionResultDisplay(
            conversionResult = conversionResult,
            isOnline,
            onRetry = { convertCurrency() }
        )

    }
}

@Composable
fun ConversionResultDisplay(
    conversionResult: ConversionResult,
    isOnline: Boolean,
    onRetry: () -> Unit,
) {
    when (conversionResult) {
        is ConversionResult.Error -> {
            Text(text = "Error: ${conversionResult.message}")
        }
        ConversionResult.NoData -> {
            if (isOnline) {
                Column {
                    Text(text = "No conversion data available")
                    Button(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }
            } else {
                Text(text = "You are not connected to the internet. Please check your connection and retry")
            }
        }
        is ConversionResult.Success -> {
            Text(text = "Conversion result: ${conversionResult.value}")
        }
        ConversionResult.Loading -> {
            CircularProgressIndicator()
        }
    }
}