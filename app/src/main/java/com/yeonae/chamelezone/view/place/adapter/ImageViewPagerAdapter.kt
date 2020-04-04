package com.yeonae.chamelezone.view.place.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.slider_image.view.*

class ImageViewPagerAdapter(val images: List<String>) : PagerAdapter() {

override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.slider_image, container, false)

        view.post {
            view.image_view.glideImageSet(
                images[position],
                view.measuredWidth,
                view.measuredHeight
            )
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun getCount(): Int =
        images.size
}


