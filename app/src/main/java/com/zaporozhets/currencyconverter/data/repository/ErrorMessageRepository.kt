package com.zaporozhets.currencyconverter.data.repository

interface ErrorMessageRepository {
    fun getAmountCannotBeBlankError(): String
    fun getInvalidAmountError(): String
    fun getAmountShouldBePositiveError(): String
    fun getUnknownError(): String
    fun getNetworkErrorOccurredMessage(): String
    fun getAmountTooLargeError(): String
}