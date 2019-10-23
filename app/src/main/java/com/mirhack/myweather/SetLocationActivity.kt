package com.mirhack.myweather

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_set_location.*
import java.util.*


class SetLocationActivity : AppCompatActivity() {
    val TABLE_NAME = "cityList"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)

        var task = SetLocationTask(this)
        task.execute()

    }
}
