package com.example.myapplication

import dev.forkhandles.result4k.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class WeatherViewModel {

    fun fetchWeatherForecast(postCode: String, callback: (Result<Weather, Error>) -> Unit) {
        GlobalScope.launch {
            callback(ApiInterface.create().getWeather(location = postCode).getResult() )
        }
    }
}