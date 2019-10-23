package com.mirhack.myweather

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_set_location.*
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class SetLocationTask(activity: Activity) : AsyncTask<Void, Void, ArrayList<ArrayList<String>>>() {
    private val activityReference = WeakReference<Activity>(activity)

    override fun onPostExecute(result: ArrayList<ArrayList<String>>?) {

        val activity = activityReference.get()
        val idList = result!![0]
        val cityList = result[1]
        val preferences = activity!!.getSharedPreferences("WeatherPreferences", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        activity.pbLoading.visibility = View.GONE
        //Устанавливаем адаптер для Auto Complete Text View

        activity.actvSetCity.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, cityList))

        activity.btnSave.setOnClickListener {
            val position = cityList.indexOf(activity.actvSetCity.text.toString())
            if (position == -1) {
                Toast.makeText(activity, "Выберите город из списка", Toast.LENGTH_LONG).show()
            } else {
                val id = idList[position]
                Log.d("myLog", id)
                editor!!.putString("city", activity.actvSetCity.text.toString()) //Сохраняем город в настройки
                editor.putString("id", id) //Сохраняем город в настройки
                editor.commit()
                activity.finish() //Закрываем Activity
            }
        }
    }

    override fun doInBackground(vararg params: Void?): ArrayList<ArrayList<String>> {
        val activity = activityReference.get()


        //Открываем файл с данными
        val fileStream = activity?.assets?.open("CityList.txt")

        val cityList = ArrayList<String>()
        val idList = ArrayList<String>()

        fileStream.use {
            val scanner: Scanner = Scanner(it)
            scanner.nextLine() //Пропускаем первую строку
            while (scanner.hasNextLine()) {

                val line = scanner.nextLine()
                //Разделяем линию на 3 части и заносим каждкю часть в отдельный массив
                idList.add(line.split(",")[0])

                cityList.add("${line.split(",")[1]}, ${line.split(",")[2]}")

            }

        }

        return arrayListOf(idList, cityList)

    }
}