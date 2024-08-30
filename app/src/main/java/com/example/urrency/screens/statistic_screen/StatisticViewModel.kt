package com.example.urrency.screens.statistic_screen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urrency.retrofit.cbr.CbrRepository
import com.example.urrency.retrofit.cbr.ValCurs
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class StatisticViewModel: ViewModel() {

    var response: MutableLiveData<ValCurs> = MutableLiveData()
    var cbrRepository: CbrRepository = CbrRepository()
    val dates = mutableListOf<String>()

    //запрос и вставка данных
    fun queryData(startDate: String,endDate: String,currencyCode: String){
        viewModelScope.launch(Dispatchers.IO) {
            val valCurs = cbrRepository.getCbrRates(startDate, endDate, currencyCode)
            response.postValue(valCurs)
        }
    }
    //добавляет нужные и полученного запроса в список из дата класов Entry + добавляет даты в список dates
    fun updateData(): List<Entry> {
        dates.clear()
        dates.add("date")
        val entryList = mutableListOf<Entry>()
        var x = 1f

        response.value?.records?.forEach {
            entryList.add(Entry(x++,it.value.replace(",", ".").toFloat()))
            dates.add(it.date.dropLast(5))
        }

        return entryList
    }

    //выдаёт дату 30 дней назад
    @SuppressLint("SimpleDateFormat")
    fun getLastDate30DaysAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val lastDate30DaysAgo = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
        return lastDate30DaysAgo
    }
}