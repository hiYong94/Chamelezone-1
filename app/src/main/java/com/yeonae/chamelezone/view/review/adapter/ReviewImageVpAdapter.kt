package com.yeonae.chamelezone.view.review.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.slider_image.view.*

class ReviewImageVpAdapter(private val images: IntArray) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slider_item_myreview_image, container, false)

        view.post {
            Log.d("size defi", "gggggggggg  ${view.measuredWidth} ${view.measuredHeight}")

            Glide.with(view.context)
                .load(images[position])
                .override(view.measuredWidth, view.measuredHeight)
                .centerCrop()
                .into(view.image_view)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images.size

}