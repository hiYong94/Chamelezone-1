package com.yeonae.chamelezone.view.review

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_review_modify.*

class ReviewModifyActivity : AppCompatActivity(), BottomSheetImagePicker.OnImagesSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_review_modify)

        tv_title.requestFocus()
        btn_back.setOnClickListener {
            finish()
        }
        setupGUI()

        tv_title.text = intent.getStringExtra("placeName")
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {

        image_container.removeAllViews()
        uris.forEach { uri ->
            val iv = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ImageView
            image_container.addView(iv)
            Glide.with(this).load(uri).into(iv)
        }
    }

    private fun pickMulti() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .multiSelect(2, 4)
            .multiSelectTitles(
                R.plurals.pick_multi,
                R.plurals.pick_multi_more,
                R.string.pick_multi_limit
            )
            .peekHeight(R.dimen.peekHeight)
            .columnSize(R.dimen.columnSize)
            .requestTag("사진이 선택되었습니다.")
            .show(supportFragmentManager)
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener { pickMulti() }
        btn_image_create.setOnClickListener { image_container.removeAllViews() }
    }
}