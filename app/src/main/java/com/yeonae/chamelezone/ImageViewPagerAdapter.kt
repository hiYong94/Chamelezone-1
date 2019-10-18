package com.yeonae.chamelezone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.slider_image.view.*

class ImageViewPager {
    private val images = intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` // as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.slider_image, container, false)
        val imageView = view.image_view
        imageView.setImageResource(images[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}

