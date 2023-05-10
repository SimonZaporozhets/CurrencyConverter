package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaporozhets.currencyconverter.utils.currencies


@Composable
fun CurrencyConverterScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        val amount = remember { mutableStateOf("") }

        val baseCurrency = remember {
            mutableStateOf(currencies[0])
        }
        val targetCurrency = remember {
            mutableStateOf(currencies[1])
        }
        val conversionResult = remember {
            mutableStateOf("Converted amount will be displayed here")
        }

        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            label = { Text("Amount") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            DropdownMenuCurrencySelector(selectedCurrency = baseCurrency, label = "Base currency")
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenuCurrencySelector(selectedCurrency = targetCurrency,
                label = "Target currency")

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = conversionResult.value)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {
            Text("Convert")
        }

    }
}

@Composable
fun DropdownMenuCurrencySelector(selectedCurrency: MutableState<String>, label: String) {
    val expanded = remember {
        mutableStateOf(false)
    }

    Column {
        Text(text = label)
        Button(onClick = { expanded.value = !expanded.value }) {
            Text(text = selectedCurrency.value)
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(onClick = {
                    selectedCurrency.value = currency
                    expanded.value = false
                }) {
                    Text(text = currency)
                }
            }
        }
    }
}