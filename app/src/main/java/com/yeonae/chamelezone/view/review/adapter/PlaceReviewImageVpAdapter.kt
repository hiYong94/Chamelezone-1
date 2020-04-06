package com.yeonae.chamelezone.view.review.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideOriginImageSet
import kotlinx.android.synthetic.main.slider_item_place_review_image.view.*

class PlaceReviewImageVpAdapter(private val images: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slider_item_place_review_image, container, false)

        view.post {
            images[position].let { view.iv_review_img.glideOriginImageSet(it, view.measuredWidth, view.measuredHeight) }
        }
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images.size

}