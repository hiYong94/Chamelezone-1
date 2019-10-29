package com.yeonae.chamelezone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import gun0912.tedbottompicker.TedBottomPicker
//import gun0912.tedbottompicker.TedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        val imageAdapter = ImageViewPagerAdapter()
        view.adapter = imageAdapter


        val uriList = listOf<Uri>()

//        TedBottomPicker.with(this)
//            .setPeekHeight(300)
//            .showTitle(false)
//            .setCompleteButtonText("Done")
//            .setEmptySelectionText("No Select")
//            .setSelectedUriList(uriList)
//            .showMultiImage { uriList ->
//                uriList
//            }

        review.setOnClickListener {
            val intent = Intent(this, ReviewCreateActivity::class.java)
            startActivity(intent)
        }
    }
}