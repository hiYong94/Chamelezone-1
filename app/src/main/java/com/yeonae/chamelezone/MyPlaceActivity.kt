package com.yeonae.chamelezone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_place.*

class MyPlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_place)

        btn_back.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, PlaceRegisterActivity::class.java)
            startActivity(intent)
        }
    }
}