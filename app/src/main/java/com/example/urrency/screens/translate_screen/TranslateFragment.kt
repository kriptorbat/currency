package com.example.urrency.screens.translate_screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.urrency.R
import com.example.urrency.databinding.FragmentTranslateBinding
import com.example.urrency.retrofit.ExchangeRates.ExchangeRatesRepository
import com.example.urrency.retrofit.ExchangeRates.ExchangeRatesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TranslateFragment : Fragment() {

    private lateinit var binding: FragmentTranslateBinding
    private lateinit var viewModel: TranslateViewModel
    lateinit var currency: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        binding = FragmentTranslateBinding.inflate(inflater)

        initialization() //Иницализация всех параметров
        spinnerListeners() //Обработчик спинеров
        updateData()

        return binding.root
    }
    //Иницализация параметров
    private fun initialization(){
        //Массив валют
        currency = resources.getStringArray(R.array.Currency)

        //ViewModel
        viewModel = ViewModelProvider(this)[TranslateViewModel::class.java]

        //адаптер для спинеров
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, currency)
        binding.spinner1.adapter = adapter
        binding.spinner2.adapter = adapter

        //обработка введённого текста в первый editText
        binding.editText1.addTextChangedListener {
            if(binding.editText1.text.isNotEmpty())
                viewModel.startTranslate(binding.editText1.text.toString().toInt())
            else binding.editText2.setText("")
        }

    }

    //Обработчики спинеров
    private fun spinnerListeners(){
        binding.spinner1.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.setCur(currency[position])
                if(binding.editText1.text.toString().isNotEmpty()) {
                    viewModel.startTranslate(binding.editText1.text.toString().toInt())
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.updatePositionCur(currency[position])
                val text = binding.editText1.text.toString()
                if(text.isNotEmpty()) {
                    viewModel.startTranslate(text.toInt())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateData(){
        viewModel.translate.observe(requireActivity(), Observer {
            if(binding.editText1.text.isNotEmpty())
                binding.editText2.setText(it.toString())
            else binding.editText2.setText("")
        })
    }
}