package com.zaporozhets.currencyconverter.domain

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}