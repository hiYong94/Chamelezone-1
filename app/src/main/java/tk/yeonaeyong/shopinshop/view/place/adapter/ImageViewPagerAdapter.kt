package tk.yeonaeyong.shopinshop.view.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slider_image.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.glideImageSet

class ImageViewPagerAdapter(val images: List<String>) : PagerAdapter() {

    private lateinit var onClickListener: OnClickListener

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

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
            onClickListener.onClick(position)
        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun getCount(): Int =
        images.size
}


