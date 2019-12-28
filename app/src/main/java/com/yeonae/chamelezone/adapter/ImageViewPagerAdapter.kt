package com.yeonae.chamelezone.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.slider_image.view.*

class ImageViewPagerAdapter : PagerAdapter() {

    val images = intArrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.slider_image, container, false)

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

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun getCount(): Int =
        images.size
}

