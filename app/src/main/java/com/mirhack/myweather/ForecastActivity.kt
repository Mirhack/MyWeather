package com.mirhack.myweather

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_forecast.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

import java.util.*

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        val preferences = getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)

        val city = preferences.getString("city", "Город")
        val cityId = preferences.getString("id", "524901")

        displayForecast(cityId, city)

    }

    private fun displayForecast(cityId: String, city: String) {
        val API_KEY = "4f27092dd56307e3b9dd5d536bf7a9b5"
        val apiService = OWMApiService.getApi()
        val units = "metric"
        val lang = "Ru"
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale(lang))

        val day1: Map<String, TextView> = mapOf("date" to tvDay1, "temp" to tvTemp1, "description" to tvDescription1)
        val day2: Map<String, TextView> = mapOf("date" to tvDay2, "temp" to tvTemp2, "description" to tvDescription2)
        val day3: Map<String, TextView> = mapOf("date" to tvDay3, "temp" to tvTemp3, "description" to tvDescription3)
        val day4: Map<String, TextView> = mapOf("date" to tvDay4, "temp" to tvTemp4, "description" to tvDescription4)

        val listDays = arrayOf(day1, day2, day3, day4)


        apiService.getForecast(cityId, API_KEY, units, lang)
                .enqueue(object : Callback<WeatherBase> {
                    //создаем анонимный обьект для обработки callback
                    override fun onFailure(call: Call<WeatherBase>, t: Throwable) {
                        Log.d("myLog", t.message)
                    }

                    override fun onResponse(call: Call<WeatherBase>, response: Response<WeatherBase>) {
                        val weatherBase = response.body()


                        var i = 0
                        for (dayForecast in weatherBase?.list!!) {


                            if (i < 4 && "12:00:00" in dayForecast.dt_txt && ((System.currentTimeMillis().div(1000) + 43200) < dayForecast.dt)) {
                                listDays[i]["date"]!!.text = dateFormat.format(Date(dayForecast.dt * 1000L)).capitalize()
                                listDays[i]["temp"]!!.text = if (dayForecast.main.temp.toInt() > 0) "+${dayForecast.main.temp.toInt()}"
                                else dayForecast.main.temp.toInt().toString()
                                listDays[i]["description"]!!.text = dayForecast.weather[0].description.capitalize()
                                i++
                            }

                        }

                        loading.visibility = View.GONE
                    }

                })

    }
}
