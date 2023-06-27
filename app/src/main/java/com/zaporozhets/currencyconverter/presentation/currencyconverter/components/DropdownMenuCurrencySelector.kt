package com.zaporozhets.currencyconverter.presentation.currencyconverter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuCurrencySelector(
    selectedCurrency: MutableState<String>,
    label: String,
    currencies: List<String>,
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = label)
        Button(
            onClick = { expanded.value = !expanded.value },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
        ) {
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