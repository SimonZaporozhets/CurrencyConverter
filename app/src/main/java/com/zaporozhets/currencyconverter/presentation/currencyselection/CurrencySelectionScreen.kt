package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaporozhets.currencyconverter.domain.model.Currency
import com.zaporozhets.currencyconverter.domain.model.UiState
import com.zaporozhets.currencyconverter.presentation.ui.theme.CurrencyConverterTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun CurrencySelectionScreen(
    state: CurrencySelectionState,
    navigationEvent: SharedFlow<String>,
    onEvent: (CurrencySelectionEvent) -> Unit,
    navController: NavController,
    currencyFor: String,
) {

    val context = LocalContext.current

    LaunchedEffect(context) {
        navigationEvent.collect { currencyName ->
            navController.previousBackStackEntry?.savedStateHandle?.apply {
                set("currency_name", currencyName)
                set("currency_for", currencyFor)
            }
            navController.popBackStack()
        }
    }

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

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                TextField(
                    value = state.searchQuery.value,
                    onValueChange = { newValue ->
                        onEvent(CurrencySelectionEvent.SearchQueryChanged(newValue))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    items(state.currencies) { currency ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .background(
                                    color = Color.DarkGray,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    onEvent(
                                        CurrencySelectionEvent.CurrencySelected(
                                            currency.symbol,
                                            currencyFor,
                                        ),
                                    )
                                }
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = currency.symbol,
                                modifier = Modifier
                            )

                            Text(
                                text = currency.name,
                                modifier = Modifier
                            )

                        }
                    }
                }
            }

        }
    })
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectionScreenPreview() {
    CurrencyConverterTheme(darkTheme = true) {
        // Define your mock Currency object here
        val currenciesList = listOf(
            Currency("USD", "United States Dollar"),
            Currency("EUR", "Euro"),
            Currency("JPY", "Japanese Yen"),
            Currency("GBP", "Pound Sterling"),
            Currency("AUD", "Australian Dollar"),
            Currency("CAD", "Canadian Dollar"),
            Currency("CHF", "Swiss Franc"),
            Currency("CNY", "Chinese Yuan"),
            Currency("SEK", "Swedish Krona"),
            Currency("NZD", "New Zealand Dollar"),
            Currency("MXN", "Mexican Peso"),
            Currency("SGD", "Singapore Dollar"),
            Currency("HKD", "Hong Kong Dollar"),
            Currency("NOK", "Norwegian Krone"),
            Currency("KRW", "South Korean Won"),
            Currency("TRY", "Turkish Lira"),
            Currency("INR", "Indian Rupee"),
            Currency("RUB", "Russian Ruble"),
            Currency("BRL", "Brazilian Real"),
            Currency("ZAR", "South African Rand")
        )

        val mockState = remember {
            CurrencySelectionState(
                searchQuery = mutableStateOf("USD"),
                currencies = mutableStateListOf(*currenciesList.toTypedArray()),
                uiState = mutableStateOf(UiState.NoData)
            )
        }
        val mockNavController = rememberNavController()
        val mockOnEvent: (CurrencySelectionEvent) -> Unit = {}
        val mockCurrencyFor = "base"

        // Provide an empty shared flow for preview
        val mockNavigationEvent = remember { MutableSharedFlow<String>() }

        CurrencySelectionScreen(
            state = mockState,
            navigationEvent = mockNavigationEvent,
            onEvent = mockOnEvent,
            navController = mockNavController,
            currencyFor = mockCurrencyFor
        )
    }
}