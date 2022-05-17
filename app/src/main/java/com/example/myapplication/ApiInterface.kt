package com.example.myapplication

import dev.forkhandles.result4k.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast.json")
    fun getWeather(
        @Query("q") location: String,
        @Query("key") key: String = "ad5cb0289b774558a1081231221505",
        @Query("aqi") aqi: String = "no",
        @Query("days") days: Int = 3,
        @Query("alerts") alerts: String = "no",
    ): Call<Weather>

    companion object {

        var BASE_URL = "http://api.weatherapi.com/v1/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
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
                    Success(Unit) as Success<T>
                } else {
                    response.body()?.let{ Success(it) } ?: Failure(Error("An Error Occurred in calling the API"))
                }
            }  else {
                Failure(Error("An Error Occurred in calling the API"))
            }
        }
}