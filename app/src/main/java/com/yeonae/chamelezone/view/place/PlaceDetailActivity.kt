package com.yeonae.chamelezone.view.place

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.ImageViewPagerAdapter
import com.yeonae.chamelezone.view.place.adapter.PlaceDetailPagerAdapter
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        btn_back.setOnClickListener {
            finish()
        }

        btn_back_2.setOnClickListener {
            finish()
        }

        val imageAdapter = ImageViewPagerAdapter()
        view.adapter = imageAdapter
        tab_layout.setupWithViewPager(view, true)

        val fragmentAdapter = PlaceDetailPagerAdapter(supportFragmentManager)
        viewpager_detail.adapter = fragmentAdapter
        tabs_detail.setupWithViewPager(viewpager_detail)

        scroll_view.viewTreeObserver.addOnScrollChangedListener {
            Log.d("ssssssssssssssssssss", "${scroll_view.scrollY}")
            Log.d("imageView", "${view.bottom}")

            if (scroll_view.scrollY <= img_layout.bottom) {
                layout_visibility.visibility = View.GONE // 화면에서 제외
                btn_back.visibility = View.VISIBLE
            } else {
                layout_visibility.visibility = View.VISIBLE // 화면에서 보이기
                btn_back.visibility = View.GONE
            }
        }
    }
}