package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentFirstBinding
import dev.forkhandles.result4k.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call

class WeatherFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel = WeatherViewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? HomeActivity)?.fetchLocation { location ->
            location?.let { displayWeather("${it.latitude},${it.longitude}") }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? HomeActivity)?.fetchLocation { location ->
            location?.let { displayWeather("${it.latitude},${it.longitude}") }
        }
        binding.editTextTextPostalAddress.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                val postCode: String = textView.text.toString()
                displayWeather(postCode)
            }
            false
        }
    }

    private fun displayWeather(location: String) {
        viewModel.fetchWeatherForecast(location) {
            activity?.runOnUiThread {
                it.map { weather ->
                    binding.titleTextView.text =
                        "Check out the weather in ${weather.location.region}"
                    binding.temperatureValueTextView.text = "${weather.current.temperature} °C"
                    binding.humidityValueTextView.text = "${weather.current.humidity}%"
                    binding.impressionValueTextView.text = "${weather.current.condition.impression}"

                    weather.forecast.dailyForecasts.firstOrNull()?.let {
                        binding.temperatureValueD1TextView.text = "${it.day.temperature} °C"
                        binding.humidityValueD1TextView.text = "${it.day.humidity}%"
                        binding.impressionValueD1TextView.text = "${it.day.condition.impression}"
                    }
                }.mapFailure {
                    Log.e("WEATHER", "Couldnt fetch weather details due to the error ${it.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}