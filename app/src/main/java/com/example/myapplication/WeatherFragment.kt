package com.example.myapplication

import android.content.Context
import android.os.Bundle
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

    private fun displayWeather(location: String){
        getWeather(location){
            it.map { weather ->
                activity?.runOnUiThread {
                    binding.titleTextView.text = "Check out the weather in ${weather.location.region}"
                    binding.temperatureValueTextView.text = "${weather.current.temperature} °C" // TODO
                    binding.humidityValueTextView.text = "${weather.current.humidity}%" // TODO
                    binding.impressionValueTextView.text = "${weather.current.condition.impression}" // TODO

                    weather.forecast.dailyForecasts.firstOrNull()?.let {
                        binding.temperatureValueD1TextView.text = "${it.day.temperature} °C" // TODO
                        binding.humidityValueD1TextView.text = "${it.day.humidity}%" // TODO
                        binding.impressionValueD1TextView.text = "${it.day.condition.impression}" // TODO
                    }

                }
            }.mapFailure {
                activity?.runOnUiThread {
                    binding.temperatureValueTextView.text = "bye"
                }
            }
        }
    }

    private fun getWeather(postCode: String, callback: (Result<Weather, Error>) -> Unit) {
        GlobalScope.launch {
           callback(ApiInterface.create().getWeather(location = postCode).getResult() )
        }
    }

    inline fun <reified T> Call<T>.getResult(): Result<T, Error> {
        return resultFrom { execute() }
            .mapFailure { exception ->
                Error(exception.toString())
            }
            .flatMap { response ->
                if (response.isSuccessful) {
                    if (T::class == Unit::class) {
                        Success(Unit) as Success<T> // TODO check this
                    } else {
                        response.body()?.let{ Success(it) } ?: Failure(Error("Empty body"))
                    }
                }  else {
                    Failure(Error("some other failure"))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}