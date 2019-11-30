package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Like
import com.yeonae.chamelezone.model.Place
import com.yeonae.chamelezone.view.mypage.myplace.adapter.MyPlaceRvAdapter
import kotlinx.android.synthetic.main.activity_my_place.*

class MyPlaceActivity : AppCompatActivity() {
    private val myPlaceList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85")
    )
    private val myPlaceRvAdapter = MyPlaceRvAdapter(myPlaceList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_place)
        setAdapter()

        myPlaceRvAdapter.setOnClickListener(object : MyPlaceRvAdapter.OnClickListener {
            override fun onClick(place: Place) {

            }
        })

        btn_back.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, PlaceRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        recycler_my_place.layoutManager = LinearLayoutManager(this)
        recycler_my_place.adapter = myPlaceRvAdapter
    }
}