package com.zaporozhets.currencyconverter.presentation.currencyselection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaporozhets.currencyconverter.utils.randomFloatInRange
import com.zaporozhets.currencyconverter.utils.shimmerEffect

@Composable
fun ShimmerCurrencyItem() {
    val randomWeight = remember { randomFloatInRange(0.2f, 0.7f) }
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .height(22.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.weight(0.9f - randomWeight))
        Box(
            modifier = Modifier
                .weight(randomWeight)
                .height(22.dp)
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShimmerCurrencyItemPreview() {
    ShimmerCurrencyItem()
}