package com.zaporozhets.currencyconverter.presentation.currencyconverter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.zaporozhets.currencyconverter.utils.currencies

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