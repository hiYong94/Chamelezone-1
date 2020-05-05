package com.yeonae.chamelezone.view.place.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.view.place.PlaceDetailActivity.Companion.MEMBER_NUMBER
import com.yeonae.chamelezone.view.place.PlaceDetailActivity.Companion.PLACE_NUMBER
import com.yeonae.chamelezone.view.place.PlaceImageDetailActivity
import kotlinx.android.synthetic.main.slider_image.view.*

class ImageViewPagerAdapter(
    val images: List<String>,
    private val placeNumber: Int,
    private val memberNumber: Int
) : PagerAdapter() {

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

        view.setOnClickListener {
            val intent = Intent(container.context, PlaceImageDetailActivity::class.java)
            val data = intent.apply { putExtra(POSITION, position) }
            intent.putExtra(PLACE_NUMBER, placeNumber)
            intent.putExtra(MEMBER_NUMBER, memberNumber)
            startActivity(container.context, data, Bundle())
        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun getCount(): Int =
        images.size

    companion object {
        const val POSITION = "position"
    }
}


