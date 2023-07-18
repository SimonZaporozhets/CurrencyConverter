package com.zaporozhets.currencyconverter.data.repository

import android.content.res.Resources
import com.zaporozhets.currencyconverter.R

class ErrorMessageRepositoryImpl(private val resources: Resources) : ErrorMessageRepository {

    override fun getAmountCannotBeBlankError(): String {
        return resources.getString(R.string.amount_cannot_be_blank)
    }

    override fun getInvalidAmountError(): String {
        return resources.getString(R.string.invalid_amount)
    }

    override fun getAmountShouldBePositiveError(): String {
        return resources.getString(R.string.amount_should_be_positive)
    }

    override fun getUnknownError(): String {
        return resources.getString(R.string.unknown_error)
    }

    override fun getNetworkErrorOccurredMessage(): String {
        return resources.getString(R.string.network_error_occurred)
    }

    override fun getAmountTooLargeError(): String {
        return resources.getString(R.string.amount_too_large)
    }

}