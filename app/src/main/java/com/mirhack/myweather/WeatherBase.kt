package com.mirhack.myweather

import com.google.gson.annotations.SerializedName

/**
 * Data from OpenStreetMap.org
 * by Mirhack :)
 * */

//Класс-модель для хранения данных

data class WeatherBase(

        @SerializedName("coord") val coord : Coord,
        @SerializedName("weather") val weather : List<Weather>,
        @SerializedName("base") val base : String,
        @SerializedName("main") val main : Main,
        @SerializedName("visibility") val visibility : Int,
        @SerializedName("wind") val wind : Wind,
        @SerializedName("clouds") val clouds : Clouds,
        @SerializedName("dt") val dt : Int,
        @SerializedName("sys") val sys : Sys,
        @SerializedName("id") val id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("cod") val cod : Int,
        @SerializedName("message") val message : Double,
        @SerializedName("city") val city : City,
        @SerializedName("cnt") val cnt : Int,
        @SerializedName("list") val list : List<ListData>
)

data class Coord (

        @SerializedName("lon") val lon : Double,
        @SerializedName("lat") val lat : Double
)

data class Main (

        @SerializedName("temp") val temp : Double,
        @SerializedName("temp_min") val temp_min : Double,
        @SerializedName("temp_max") val temp_max : Double,
        @SerializedName("pressure") val pressure : Double,
        @SerializedName("sea_level") val sea_level : Double,
        @SerializedName("grnd_level") val grnd_level : Double,
        @SerializedName("humidity") val humidity : Double,
        @SerializedName("temp_kf") val temp_kf : Double
)

data class Sys (

        @SerializedName("type") val type : Int,
        @SerializedName("id") val id : Int,
        @SerializedName("pod") val pod : String,
        @SerializedName("message") val message : Double,
        @SerializedName("country") val country : String,
        @SerializedName("sunrise") val sunrise : Int,
        @SerializedName("sunset") val sunset : Int
)

data class Weather (

        @SerializedName("id") val id : Int,
        @SerializedName("main") val main : String,
        @SerializedName("description") val description : String,
        @SerializedName("icon") val icon : String
)

data class Wind (

        @SerializedName("speed") val speed : Double,
        @SerializedName("deg") val deg : Double
)

data class Clouds (

        @SerializedName("all") val all : Int
)



data class Temp (

        @SerializedName("day") val day : Double,
        @SerializedName("min") val min : Double,
        @SerializedName("max") val max : Double,
        @SerializedName("night") val night : Double,
        @SerializedName("eve") val eve : Double,
        @SerializedName("morn") val morn : Double
)

data class ListData (

        @SerializedName("dt") val dt : Int,
        @SerializedName("main") val main : Main,
        @SerializedName("temp") val temp : Double,
        @SerializedName("pressure") val pressure : Double,
        @SerializedName("humidity") val humidity : Int,
        @SerializedName("weather") val weather : List<Weather>,
        @SerializedName("speed") val speed : Double,
        @SerializedName("deg") val deg : Int,
        @SerializedName("clouds") val clouds : Clouds,
        @SerializedName("snow") val snow : Snow,
        @SerializedName("dt_txt") val dt_txt : String
)

data class City (

        @SerializedName("geoname_id") val geoname_id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("id") val id : Int,
        @SerializedName("coord") val coord : Coord,
        @SerializedName("lat") val lat : Double,
        @SerializedName("lon") val lon : Double,
        @SerializedName("country") val country : String,
        @SerializedName("iso2") val iso2 : String,
        @SerializedName("type") val type : String,
        @SerializedName("population") val population : Int
)

data class Snow (
        @SerializedName("3h") val snow3h : Double
)

data class Rain (
        @SerializedName("3h") val rain3h : Double
)