package com.example.urrency.retrofit.ExchangeRates

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeRatesRepository {
    private var apiService: ExchangeRatesApiService
    init {
        val apiKey = "f9a7f660244ac2829cf1d5d6"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/$apiKey/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ExchangeRatesApiService::class.java)
    }

    suspend fun getExchangeRates(baseCurrency: String): ExchangeRatesResponse? {
        return try {
            apiService.getExchangeRates(baseCurrency)
        } catch (e: Exception) {
            null
        }
    }
}