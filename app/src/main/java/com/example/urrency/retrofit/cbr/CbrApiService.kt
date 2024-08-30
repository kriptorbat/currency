package com.example.urrency.retrofit.cbr

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CbrApiService {
    @GET("XML_dynamic.asp")
    suspend fun getExchangeRate(
        @Query("date_req1") startDate: String,
        @Query("date_req2") endDate: String,
        @Query("VAL_NM_RQ") currencyCode: String
    ): ValCurs//CbrResponse//Response<ResponseBody> // Используем Response<ResponseBody> для работы с XML
}