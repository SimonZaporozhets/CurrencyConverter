package com.zaporozhets.currencyconverter.presentation.currencyconverter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaporozhets.currencyconverter.R
import com.zaporozhets.currencyconverter.presentation.currencyconverter.HomeUiState

@Composable
fun ConversionResultDisplay(
    homeUiState: HomeUiState,
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
            when (homeUiState) {
                is HomeUiState.Error -> {
                    Text(
                        text = stringResource(id = R.string.error, homeUiState.message),
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }

                HomeUiState.NoData -> {
                    Text(
                        text = stringResource(id = R.string.no_conversion_data_available),
                        textAlign = TextAlign.Center
                    )
                }

                is HomeUiState.Success -> {
                    Text(
                        text = stringResource(id = R.string.conversion_result, homeUiState.value),
                        textAlign = TextAlign.Center
                    )
                }

                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConversionResultDisplay() {
    ConversionResultDisplay(
        homeUiState = HomeUiState.NoData,
        onRetry = { /*TODO handle retry*/ }
    )
}