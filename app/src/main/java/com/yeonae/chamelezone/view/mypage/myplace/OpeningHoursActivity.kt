package com.yeonae.chamelezone.view.mypage.myplace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_opening_hours.*

class OpeningHoursActivity : AppCompatActivity() {
    private val openingHours = ArrayList<String>()
    private val openingHoursPosition = ArrayList<String>()
    private var selectedPosition = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_hours)
        selectedPosition = intent.getStringArrayListExtra("selectedPosition")
        selectedPosition.forEach {
            val openingHours = it.split(" ")
            when {
                openingHours[0] == checkbox_sun.text -> {
                    checkbox_sun.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_sun,
                        1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_mon.text -> {
                    checkbox_mon.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_mon, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_tue.text -> {
                    checkbox_tue.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_tue, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_wed.text -> {
                    checkbox_wed.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_wed, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_thu.text -> {
                    checkbox_thu.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_thu, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_fri.text -> {
                    checkbox_fri.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_fri, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }
                openingHours[0] == checkbox_sat.text -> {
                    checkbox_sat.isChecked = true
                    addOpeningHourLayout(
                        opening_hour_sat, 1,
                        openingHours[1].toInt(),
                        openingHours[2].toInt()
                    )
                }

            }
        }

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
            } else if (!checkbox_mon.isChecked) {
                addOpeningHourLayout(opening_hour_mon, 0)
            }
        }

        checkbox_tue.setOnClickListener {
            if (checkbox_tue.isChecked) {
                addOpeningHourLayout(opening_hour_tue, 1)
            } else if (!checkbox_tue.isChecked) {
                addOpeningHourLayout(opening_hour_tue, 0)
            }
        }

        checkbox_wed.setOnClickListener {
            if (checkbox_wed.isChecked) {
                addOpeningHourLayout(opening_hour_wed, 1)
            } else if (!checkbox_wed.isChecked) {
                addOpeningHourLayout(opening_hour_wed, 0)
            }
        }

        checkbox_thu.setOnClickListener {
            if (checkbox_thu.isChecked) {
                addOpeningHourLayout(opening_hour_thu, 1)
            } else if (!checkbox_thu.isChecked) {
                addOpeningHourLayout(opening_hour_thu, 0)
            }
        }

        checkbox_fri.setOnClickListener {
            if (checkbox_fri.isChecked) {
                addOpeningHourLayout(opening_hour_fri, 1)
            } else if (!checkbox_fri.isChecked) {
                addOpeningHourLayout(opening_hour_fri, 0)
            }
        }

        checkbox_sat.setOnClickListener {
            if (checkbox_sat.isChecked) {
                addOpeningHourLayout(opening_hour_sat, 1)
            } else if (!checkbox_sat.isChecked) {
                addOpeningHourLayout(opening_hour_sat, 0)
            }
        }

        btn_complete.setOnClickListener {
            if (checkbox_sun.isChecked) {
                result(opening_hour_sun, getString(R.string.sunday))
            } else {
                openingHours.add(getString(R.string.sunday_closed))
            }
            if (checkbox_mon.isChecked) {
                result(opening_hour_mon, getString(R.string.monday))
            } else {
                openingHours.add(getString(R.string.monday_closed))
            }
            if (checkbox_tue.isChecked) {
                result(opening_hour_tue, getString(R.string.tuesday))
            } else {
                openingHours.add(getString(R.string.tuesday_closed))
            }
            if (checkbox_wed.isChecked) {
                result(opening_hour_wed, getString(R.string.wednesday))
            } else {
                openingHours.add(getString(R.string.wednesday_closed))
            }
            if (checkbox_thu.isChecked) {
                result(opening_hour_thu, getString(R.string.thursday))
            } else {
                openingHours.add(getString(R.string.thursday_closed))
            }
            if (checkbox_fri.isChecked) {
                result(opening_hour_fri, getString(R.string.friday))
            } else {
                openingHours.add(getString(R.string.friday_closed))
            }
            if (checkbox_sat.isChecked) {
                result(opening_hour_sat, getString(R.string.saturday))
            } else {
                openingHours.add(getString(R.string.saturday_closed))
            }
            val intent = Intent(this, PlaceRegisterActivity::class.java)
            intent.putExtra("openingHours", openingHours)
            intent.putExtra("openingHoursPosition", openingHoursPosition)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun addOpeningHourLayout(
        linearLayout: LinearLayout,
        num: Int,
        open: Int = 0,
        close: Int = 0
    ) {
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

        layout.findViewById<Spinner>(R.id.open_spinner).setSelection(open)
        layout.findViewById<Spinner>(R.id.close_spinner).setSelection(close)

        if (num == 1) {
            linearLayout.addView(layout)
        } else {
            linearLayout.removeViewAt(0)
        }

    }

    private fun result(layoutId: LinearLayout, day: String) {
        val open = layoutId.findViewById<Spinner>(R.id.open_spinner).selectedItem
        val openPosition = layoutId.findViewById<Spinner>(R.id.open_spinner).selectedItemPosition
        val close = layoutId.findViewById<Spinner>(R.id.close_spinner).selectedItem
        val closePosition = layoutId.findViewById<Spinner>(R.id.close_spinner).selectedItemPosition
        openingHoursPosition.add("$day $openPosition $closePosition")
        openingHours.add("$day $open ${"~"} $close")
    }
}