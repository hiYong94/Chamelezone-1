package com.yeonae.chamelezone.view.course

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.activity_course_register.*

class CourseRegisterActivity : AppCompatActivity() {
    private val placeChoiceFragment = PlaceChoiceFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_register)

        btn_back.setOnClickListener {
            finish()
        }

        btn_place1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_place_choice, placeChoiceFragment.newInstance("1"))
                .addToBackStack(null)
                .commit()
        }

        btn_place2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_place_choice, placeChoiceFragment.newInstance("2"))
                .addToBackStack(null)
                .commit()
        }

        btn_place3.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_place_choice, placeChoiceFragment.newInstance("3"))
                .addToBackStack(null)
                .commit()
        }

        btn_close1.setOnClickListener {
            tv_place_name1.text = ""
            tv_place_keyword1.text = ""
            tv_place_address1.text = ""
            layout_place1.visibility = View.VISIBLE
            layout_place2.visibility = View.VISIBLE
            layout_course1.visibility = View.GONE
            if (tv_place_name2.text != "") {
                layout_place2.visibility = View.GONE
            }
        }

        btn_close2.setOnClickListener {
            tv_place_name2.text = ""
            tv_place_keyword2.text = ""
            tv_place_address2.text = ""
            layout_place2.visibility = View.VISIBLE
            layout_course2.visibility = View.GONE
            if (tv_place_name1.text != "") {
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            } else {
                layout_place1.visibility = View.VISIBLE
                layout_course1.visibility = View.GONE
            }
            if (tv_place_name3.text != "") {
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
            } else {
                layout_place3.visibility = View.VISIBLE
                layout_course3.visibility = View.GONE
            }
        }

        btn_close3.setOnClickListener {
            tv_place_name3.text = ""
            tv_place_keyword3.text = ""
            tv_place_address3.text = ""
            layout_place3.visibility = View.VISIBLE
            layout_course3.visibility = View.GONE
            if (tv_place_name1.text != "") {
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            } else {
                layout_place1.visibility = View.VISIBLE
                layout_course1.visibility = View.GONE
            }
            if (tv_place_name2.text != "") {
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
            } else {
                layout_place2.visibility = View.VISIBLE
                layout_course2.visibility = View.GONE
            }
        }
    }

    fun getVisible(visible: String, place: Place) {
        when (visible) {
            "1" -> {
                tv_place_name1.text = place.placeName
                tv_place_keyword1.text = place.placeKeyword
                tv_place_address1.text = place.placeAddress
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place2.visibility = View.GONE
                if (tv_place_name2.text == "") {
                    layout_place2.visibility = View.VISIBLE
                }

            }
            "2" -> {
                tv_place_name2.text = place.placeName
                tv_place_keyword2.text = place.placeKeyword
                tv_place_address2.text = place.placeAddress
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
                if (tv_place_name1.text == "") {
                    layout_place1.visibility = View.VISIBLE
                    layout_course1.visibility = View.GONE
                }
                if (tv_place_name3.text == "") {
                    layout_place3.visibility = View.VISIBLE
                    layout_course3.visibility = View.GONE
                }

            }
            "3" -> {
                tv_place_name3.text = place.placeName
                tv_place_keyword3.text = place.placeKeyword
                tv_place_address3.text = place.placeAddress
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
                if (tv_place_name1.text == "") {
                    layout_place1.visibility = View.VISIBLE
                    layout_course1.visibility = View.GONE
                }
                if (tv_place_name2.text == "") {
                    layout_place2.visibility = View.VISIBLE
                    layout_course2.visibility = View.GONE
                }
            }
        }
    }
}