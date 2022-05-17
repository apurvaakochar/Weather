package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class Weather(
    val current: CurrentDetails,
    val location: Location,
    val forecast: Forecast
)

data class Location(
    val region: String
)

data class CurrentDetails(
    @SerializedName("temp_c")
    val temperature: Double,
    val condition: Condition,
    val humidity: Double
)

data class Forecast(
    @SerializedName("forecastday")
    val dailyForecasts: List<DailyForecast>
)

data class Condition(
    @SerializedName("text")
    val impression: String
)

data class DailyForecast(
    val day: DayOfForecast
)

data class DayOfForecast(
    @SerializedName("avgtemp_c")
    val temperature: Double,
    val condition: Condition,
    @SerializedName("avghumidity")
    val humidity: Double
)
