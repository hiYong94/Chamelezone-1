package com.yeonae.chamelezone.view.review

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_review_create.*
import kotlinx.android.synthetic.main.activity_review_create.imageContainer
import kotlinx.android.synthetic.main.fragment_review_modify.*

class ReviewModifyFragment : Fragment(), BottomSheetImagePicker.OnImagesSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_review_modify, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_title.requestFocus()
        setupGUI()
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {

        imageContainer.removeAllViews()
        uris.forEach { uri ->
            val iv = LayoutInflater.from(this.context).inflate(
                R.layout.slider_item_image,
                imageContainer,
                false
            ) as ImageView
            imageContainer.addView(iv)
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
            .show(childFragmentManager)
    }

    private fun setupGUI() {
        btn_img_create.setOnClickListener { pickMulti() }
        btn_img_clear.setOnClickListener { imageContainer.removeAllViews() }
    }
}