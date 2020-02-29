package com.yeonae.chamelezone.view.mypage.myplace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_opening_hours.*

class OpeningHoursActivity : AppCompatActivity() {
    private val openingTime = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_hours)

        btn_back.setOnClickListener {
            finish()
        }

        checkbox_sun.setOnClickListener {
            if (checkbox_sun.isChecked) {
                addOpeningHourLayout(opening_hour_sun, 1)
            } else {
                addOpeningHourLayout(opening_hour_sun, 0)
            }
        }

        checkbox_mon.setOnClickListener {
            if (checkbox_mon.isChecked) {
                addOpeningHourLayout(opening_hour_mon, 1)
            } else {
                addOpeningHourLayout(opening_hour_mon, 0)
            }
        }

        checkbox_tue.setOnClickListener {
            if (checkbox_tue.isChecked) {
                addOpeningHourLayout(opening_hour_tue, 1)
            } else {
                addOpeningHourLayout(opening_hour_tue, 0)
            }
        }

        checkbox_wed.setOnClickListener {
            if (checkbox_wed.isChecked) {
                addOpeningHourLayout(opening_hour_wed, 1)
            } else {
                addOpeningHourLayout(opening_hour_wed, 0)
            }
        }

        checkbox_thu.setOnClickListener {
            if (checkbox_thu.isChecked) {
                addOpeningHourLayout(opening_hour_thu, 1)
            } else {
                addOpeningHourLayout(opening_hour_thu, 0)
            }
        }

        checkbox_fri.setOnClickListener {
            if (checkbox_fri.isChecked) {
                addOpeningHourLayout(opening_hour_fri, 1)
            } else {
                addOpeningHourLayout(opening_hour_fri, 0)
            }
        }

        checkbox_sat.setOnClickListener {
            if (checkbox_sat.isChecked) {
                addOpeningHourLayout(opening_hour_sat, 1)
            } else {
                addOpeningHourLayout(opening_hour_sat, 0)
            }
        }

        btn_complete.setOnClickListener {
            if(checkbox_sun.isChecked){
                result(opening_hour_sun, "일요일")
            }
            if(checkbox_mon.isChecked){
                result(opening_hour_mon, "월요일")
            }
            if(checkbox_tue.isChecked){
                result(opening_hour_tue, "화요일")
            }
            if(checkbox_wed.isChecked){
                result(opening_hour_wed, "수요일")
            }
            if(checkbox_thu.isChecked){
                result(opening_hour_thu, "목요일")
            }
            if(checkbox_fri.isChecked){
                result(opening_hour_fri, "금요일")
            }
            if(checkbox_sat.isChecked){
                result(opening_hour_sat, "토요일")
            }
            Log.d("Spinner value is", "$openingTime")
        }

    }

    private fun addOpeningHourLayout(linearLayout: LinearLayout, num: Int) {
        val openArray = resources.getStringArray(R.array.open_array)
        val closeArray = resources.getStringArray(R.array.close_array)

        val openAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, openArray)
        val closeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, closeArray)

        val layout = LayoutInflater.from(applicationContext)
            .inflate(R.layout.item_opening_hours, linearLayout, false)

        layout.findViewById<Spinner>(R.id.open_spinner).adapter = openAdapter
        layout.findViewById<Spinner>(R.id.close_spinner).adapter = closeAdapter

        if (num == 1) {
            linearLayout.addView(layout)
        } else {
            linearLayout.removeViewAt(0)
        }

    }

    private fun result(layoutId: LinearLayout, day: String) {
        val open = layoutId.findViewById<Spinner>(R.id.open_spinner).selectedItem
        val close = layoutId.findViewById<Spinner>(R.id.close_spinner).selectedItem
        openingTime.add("$day $open ${"~"} $close")
    }
}