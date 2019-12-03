package com.yeonae.chamelezone.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.PlaceChoiceFragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_course_register.*

class CourseRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_register)

        val placeChoiceFragment = PlaceChoiceFragment()
        btn_back.setOnClickListener {
            finish()
        }

        btn_place.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_place_choice, placeChoiceFragment).addToBackStack(null).commit()
        }

    }
}