package com.zaporozhets.currencyconverter.presentation.currencyselection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CurrencySelectionScreen(
    state: CurrencySelectionState,
    onEvent: (CurrencySelectionEvent) -> Unit,
    navController: NavController,
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

            Column() {
                TextField(
                    value = state.searchQuery.value,
                    onValueChange = { newValue ->
                        onEvent(CurrencySelectionEvent.SearchQueryChanged(newValue))
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn() {
                    items(state.currencies) { currency ->
                        Text(
                            text = currency,
                            Modifier.clickable {
                                onEvent(
                                    CurrencySelectionEvent.CurrencySelected(currency)
                                )
                            })
                    }
                }
            }

        }
    })
}