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
import androidx.navigation.NavController
import com.zaporozhets.currencyconverter.R
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.utils.BASE_CURRENCY
import com.zaporozhets.currencyconverter.utils.Screen
import com.zaporozhets.currencyconverter.utils.TARGET_CURRENCY


@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navController: NavController,
    selectedCurrency: String,
    currencyFor: String,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            if (selectedCurrency.isNotBlank() && currencyFor.isNotBlank()) {
                when (currencyFor) {
                    BASE_CURRENCY -> onEvent(HomeEvent.UpdateBaseCurrency(selectedCurrency))
                    TARGET_CURRENCY -> onEvent(HomeEvent.UpdateTargetCurrency(selectedCurrency))
                }
            }

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
                            onValueChange = { newAmount ->
                                onEvent(HomeEvent.ChangeAmount(newAmount))
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

                            Button(
                                onClick = {
                                    navController.navigate(
                                        Screen.CurrencySelectionScreen.withArgs(
                                            BASE_CURRENCY
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.LightGray),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
                            ) {
                                Text(state.baseCurrency.value)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Screen.CurrencySelectionScreen.withArgs(
                                            TARGET_CURRENCY
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color.LightGray),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray)
                            ) {
                                Text(state.targetCurrency.value)
                            }

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
                        text = stringResource(id = R.string.error, uiState.message),
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
                UiState.NoData -> {
                    Text(
                        text = stringResource(id = R.string.no_data_available),
                        textAlign = TextAlign.Center
                    )
                }
                is UiState.ConversionSuccess -> {
                    Text(
                        text = stringResource(id = R.string.conversion_result, uiState.value),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {}
            }
        }
    }
}