package com.example.urrency.retrofit.cbr

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class CbrRepository {
    private var apiService: CbrApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr.ru/scripts/")
            .addConverterFactory(SimpleXmlConverterFactory.create()) // Добавляем конвертер для работы с XML
            .build()

        apiService = retrofit.create(CbrApiService::class.java)
    }

    suspend fun getCbrRates(startDate: String, endDate: String, currencyCode: String): ValCurs {
        return apiService.getExchangeRate(startDate,endDate,currencyCode)
        //try {
        //} catch (e: Exception) {
        //    null
        //}
    }

}