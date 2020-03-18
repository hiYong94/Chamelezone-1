package com.yeonae.chamelezone.view.mypage.myreview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.glideOriginImageSet
import kotlinx.android.synthetic.main.slideer_item_myreview_image_detail.view.*
import kotlinx.android.synthetic.main.slider_image.view.*

class MyReviewImageDetailVpAdapter(private val images: List<String>?) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slideer_item_myreview_image_detail, container, false)

        view.post {
            images?.get(position)?.let { view.iv_review_img.glideOriginImageSet(it, view.measuredWidth, view.measuredHeight) }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images?.size!!
}