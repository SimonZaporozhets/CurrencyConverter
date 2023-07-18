package com.zaporozhets.currencyconverter.utils

import kotlin.random.Random

fun randomFloatInRange(min: Float, max: Float): Float {
    require(min <= max) { "Invalid range" }
    return min + (Random.nextFloat() * (max - min))
}
