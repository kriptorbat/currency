package com.example.urrency.screens.statistic_screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.urrency.R
import com.example.urrency.databinding.FragmentStatisticBinding
import com.example.urrency.retrofit.cbr.CbrRepository
import com.example.urrency.retrofit.cbr.ValCurs
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class StatisticFragment : Fragment() {

    lateinit var binding: FragmentStatisticBinding
    lateinit var viewModel: StatisticViewModel
    lateinit var currencyCBR: Array<String>
    lateinit var currencyCBRStandart: Array<String>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentStatisticBinding.inflate(inflater)

        initialization() //Иницализация всех объектов
        spinnerListener() //Обработчик спенера
        update()

        return binding.root
    }
    //Иницализация объектов
    private fun initialization(){

        viewModel = ViewModelProvider(this)[StatisticViewModel::class.java]

        currencyCBR = requireActivity().resources.getStringArray(R.array.CurrencyCBR) //массив кода валют для центрального банка россии
        currencyCBRStandart = requireActivity().resources.getStringArray(R.array.CurrencyCBRStandart) //массив международных абривиатур для валют

        //адаптер для спинера
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, currencyCBRStandart)
        binding.spinner.adapter = adapter

        //ретрофит :)

        // Настройка внешнего вида графика
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.description.isEnabled = false
        binding.chart.setBackgroundColor(requireActivity().getColor(R.color.color1))
        binding.chart.xAxis.textColor = Color.WHITE
        binding.chart.axisLeft.textColor = Color.WHITE
        binding.chart.axisRight.textColor = Color.WHITE
        binding.chart.legend.isEnabled = false
    }

    //Обработчики спинера
    private fun spinnerListener() {
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            @SuppressLint("SimpleDateFormat")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.queryData(viewModel.getLastDate30DaysAgo(),SimpleDateFormat("dd/MM/yyyy").format(Date()).toString(),currencyCBR[position])

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun update(){
        viewModel.response.observe(requireActivity(), Observer {
            setChart()
        })
    }


    //загрузка данных в график
    private fun setChart(){
        val entriesBeta= viewModel.updateData()

        val dataSet = LineDataSet(entriesBeta,"Стоимость рубля")
        if(isAdded){
            dataSet.color = requireActivity().getColor(R.color.color3) // Задаем цвет линии графика
            dataSet.setCircleColor(requireActivity().getColor(R.color.color2))
        }
        dataSet.valueTextColor = Color.WHITE
        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.dates)
        binding.chart.animateX(1000)
        binding.chart.invalidate()
    }
}