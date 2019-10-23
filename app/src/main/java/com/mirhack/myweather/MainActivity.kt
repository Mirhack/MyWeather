package com.mirhack.myweather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)
        val city = preferences.getString("city", "Город").split(",")[0]
        val cityId = preferences.getString("id", "524901")

        tvCity.setOnClickListener { startActivity(Intent(this, SetLocationActivity::class.java)) }
        tvTemp.setOnClickListener { startActivity(Intent(this, ForecastActivity::class.java)) }

        displayWeather(cityId, city)
    }

    override fun onResume() {
        super.onResume()
        val preferences = getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)
        val city = preferences.getString("city", "Город").split(",")[0]
        val cityId = preferences.getString("id", "524901")

        displayWeather(cityId, city)


    }

    private fun displayWeather(cityId: String, city: String) {
        val API_KEY = "apiKeyHERE"
        val apiService = OWMApiService.getApi()
        val units = "metric"
        val lang = "ru"

        apiService.getCurrentWeather(cityId, API_KEY, units, lang)
                .enqueue(object : Callback<WeatherBase> { //создаем анонимный обьект для обработки callback
                    override fun onFailure(call: Call<WeatherBase>, t: Throwable) {
                        Log.d("myLog", t.message)
                    }

                    override fun onResponse(call: Call<WeatherBase>, response: Response<WeatherBase>) {
                        val weatherBase = response.body()
                        val temperature = weatherBase?.main?.temp?.toInt()
                        var iconName = weatherBase?.weather?.get(0)?.icon!!  //Получаем название icon
                        var modifier = ""
                        val cSet = ConstraintSet()

                        //                        Устанавлваем знак температуры + или - и модификатор
                        if (temperature != null) {
                            modifier = if (temperature <= 0) {
                                tvTemp.text = temperature.toString()
                                "n"
                            } else {
                                tvTemp.text = "+$temperature"
                                "p"
                            }
                        }
                       // iconName = "50n"
                       // modifier = "n"
                        Log.d("tag", iconName)
                        cSet.load(this@MainActivity,
                                resources.getIdentifier("l$iconName$modifier", "layout", "com.mirhack.myweather"))
                        cSet.applyTo(constraint)

                        imageView.setImageResource(
                                resources.getIdentifier("b$iconName$modifier", "drawable", "com.mirhack.myweather"))


                        //                        Делаем первую букву звглавной
                        val description = weatherBase.weather.get(0).description.substring(0, 1).toUpperCase() + weatherBase.weather.get(0).description.substring(1)
                        tvDescription.text = description
                        setColor(iconName, modifier)

                        if (city.length > 12){
                            tvCity.textSize = 28F
                        } else tvCity.textSize = 32F
                        tvCity.text = city
                    }
                })
    }

    fun setColor(iconName: String, modifier: String) {
        tvCity.setTextColor(ContextCompat.getColor(this,
                resources.getIdentifier("city$iconName$modifier", "color", "com.mirhack.myweather")))
        tvTemp.setTextColor(ContextCompat.getColor(this,
                resources.getIdentifier("temp$iconName$modifier", "color", "com.mirhack.myweather")))
        tvDescription.setTextColor(ContextCompat.getColor(this,
                resources.getIdentifier("description$iconName$modifier", "color", "com.mirhack.myweather")))

    }

}


