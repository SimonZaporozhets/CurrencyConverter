package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zaporozhets.currencyconverter.R
import com.zaporozhets.currencyconverter.domain.model.ConversionResult
import com.zaporozhets.currencyconverter.presentation.currencyconverter.components.DropdownMenuCurrencySelector
import com.zaporozhets.currencyconverter.utils.currencies


@Composable
fun CurrencyConverterScreen(currencyViewModel: CurrencyConverterViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Currency Converter") })
    }, content = { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            val conversionResult by currencyViewModel.conversionResult
            val amount = remember { mutableStateOf("") }
            val amountError = remember { mutableStateOf<String?>(null) }
            val baseCurrency = remember { mutableStateOf(currencies[0]) }
            val targetCurrency = remember { mutableStateOf(currencies[1]) }

            fun convertCurrency() {
                val amountValue = amount.value.toDoubleOrNull()
                if (amountValue != null && amountValue > 0) {
                    currencyViewModel.convertCurrency(
                        amountValue, baseCurrency.value, targetCurrency.value
                    )
                    amountError.value = null
                } else {
                    amountError.value = "Invalid Amount"
                }
            }

            Column {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {

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

                            DropdownMenuCurrencySelector(
                                selectedCurrency = baseCurrency,
                                label = stringResource(R.string.base_currency)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            DropdownMenuCurrencySelector(
                                selectedCurrency = targetCurrency,
                                label = stringResource(R.string.target_currency)
                            )

                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = { convertCurrency() },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
                    ) {
                        Text(stringResource(R.string.convert))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ConversionResultDisplay(
                        conversionResult = conversionResult,
                        onRetry = { convertCurrency() },
                    )
                }
            }

        }
    })

}

@Composable
fun ConversionResultDisplay(
    conversionResult: ConversionResult,
    onRetry: () -> Unit,
) {

    Card(
        elevation = 6.dp,
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (conversionResult) {
                is ConversionResult.Error -> {
                    Text(
                        text = "Error: ${conversionResult.message}",
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }

                ConversionResult.NoData -> {
                    Text(
                        text = "No conversion data available. Please enter an amount and select currencies to convert.",
                        textAlign = TextAlign.Center
                    )
                }


                is ConversionResult.Success -> {
                    Text(
                        text = "Conversion result: ${conversionResult.value}",
                        textAlign = TextAlign.Center
                    )
                }

                ConversionResult.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}