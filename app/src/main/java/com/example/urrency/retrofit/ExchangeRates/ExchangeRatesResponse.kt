package com.example.urrency.retrofit.ExchangeRates

data class ExchangeRatesResponse(
    val base_code: String,
    val conversion_rates: Map<String, Double>
)
