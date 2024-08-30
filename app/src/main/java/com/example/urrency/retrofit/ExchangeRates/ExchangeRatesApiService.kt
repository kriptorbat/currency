package com.example.urrency.retrofit.ExchangeRates

import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRatesApiService {
    @GET("latest/{baseCurrency}")
    suspend fun getExchangeRates(@Path("baseCurrency") baseCurrency: String): ExchangeRatesResponse
}