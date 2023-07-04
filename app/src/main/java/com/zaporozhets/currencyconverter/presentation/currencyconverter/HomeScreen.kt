package com.zaporozhets.currencyconverter.presentation.currencyconverter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zaporozhets.currencyconverter.R
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.presentation.currencyconverter.components.DropdownMenuCurrencySelector


@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Currency Converter") })
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            if (state.uiState.value == UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                            value = state.amountToConvert.value,
                            onValueChange = {
                                state.amountToConvert.value = it
                                state.validationError.value = ""
                            },
                            label = { Text(stringResource(R.string.amount)) },
                            isError = state.validationError.value.isNotBlank()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (state.validationError.value.isNotBlank()) {
                            Text(
                                text = state.validationError.value,
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row {

                            DropdownMenuCurrencySelector(
                                selectedCurrency = state.baseCurrency,
                                label = stringResource(R.string.base_currency),
                                state.currencies
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            DropdownMenuCurrencySelector(
                                selectedCurrency = state.targetCurrency,
                                label = stringResource(R.string.target_currency),
                                state.currencies
                            )

                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = { onEvent(HomeEvent.ConvertCurrency) },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
                    ) {
                        Text(stringResource(R.string.convert))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ConversionResultDisplay(
                        uiState = state.uiState.value,
                        onRetry = { onEvent(HomeEvent.ConvertCurrency) },
                    )
                }
            }

        }
    })

}

@Composable
fun ConversionResultDisplay(
    uiState: UiState,
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
            when (uiState) {
                is UiState.Error -> {
                    Text(
                        text = "Error: ${uiState.message}",
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }

                UiState.NoData -> {
                    Text(
                        text = "No conversion data available. Please enter an amount and select currencies to convert.",
                        textAlign = TextAlign.Center
                    )
                }

                is UiState.ConversionSuccess -> {
                    Text(
                        text = "Conversion result: ${uiState.value}",
                        textAlign = TextAlign.Center
                    )
                }

                else -> {}
            }
        }
    }
}