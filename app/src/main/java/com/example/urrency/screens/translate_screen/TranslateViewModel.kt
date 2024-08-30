package com.example.urrency.screens.translate_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urrency.retrofit.ExchangeRates.ExchangeRatesRepository
import com.example.urrency.retrofit.ExchangeRates.ExchangeRatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel(){
    private var repository = ExchangeRatesRepository()
    lateinit var response: ExchangeRatesResponse
    var translate: MutableLiveData<Double?> = MutableLiveData()
    var positionCur: String


    init {
        translate.value = 0.0
        positionCur = ""
    }
    //Запрос на получение данных с сервера
    fun setCur(cur: String){
        if(cur.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO){
                response = repository.getExchangeRates(cur)!!
            }
        }
    }

    fun startTranslate(value: Int){
        val ss = response.conversion_rates[positionCur]!!
        translate.value = ss * value
    }

    fun updatePositionCur(result: String){
        positionCur = result
    }
}