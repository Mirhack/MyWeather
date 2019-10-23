package com.mirhack.myweather

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OWMApiService {
    @GET("data/2.5/weather")  // команда на сервере
    fun getCurrentWeather(@Query("id") cityId: String,
                          @Query("appid") appId: String,
                          @Query("units") units: String,
                          @Query("lang") lang: String): Call<WeatherBase> //Возвращаем обьект Call типа WeatherBase


    @GET("data/2.5/forecast")  // команда на сервере
    fun getForecast(@Query("id") cityId: String,
                    @Query("appid") appId: String,
                    @Query("units") units: String,
                    @Query("lang") lang: String): Call<WeatherBase> //Возвращаем обьект Call типа WeatherBase

    /**
     * Companion object to create the OWMApi Service
     */


    companion object Controller {
        val BASE_URL = "http://api.openweathermap.org/"

        fun getApi(): OWMApiService {
//            получаем объект Retrofit, содержащий базовый URL и способность преобразовывать JSON-данные с
//            помощью указанного конвертера Gson
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL) //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build()

//             в его методе create() указываем наш класс интерфейса с запросами к сайту.
            val apiService: OWMApiService = retrofit.create(OWMApiService::class.java) //Создаем объект, при помощи которого будем выполнять запросы

            return apiService
        }
    }
}