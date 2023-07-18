package com.zaporozhets.currencyconverter.domain.model

data class Currency(
    val symbol: String,
    val name: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            symbol,
            name,
            "$symbol $name",
            "$name $symbol"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}