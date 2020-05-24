package tk.yeonaeyong.shopinshop.view.mypage.myreview.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slider_item_myreview_image.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.glideImageSet
import tk.yeonaeyong.shopinshop.view.mypage.myreview.MyReviewDetailActivity.Companion.PLACE_NUMBER
import tk.yeonaeyong.shopinshop.view.mypage.myreview.MyReviewDetailActivity.Companion.REVIEW_NUMBER
import tk.yeonaeyong.shopinshop.view.mypage.myreview.MyReviewImageDetailActivity

class ReviewImageVpAdapter(
    private val images: List<String>
) : PagerAdapter() {

    private lateinit var onClickListener: OnClickListener

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slider_item_myreview_image, container, false)

        view.post {
            view.image_view.glideImageSet(images[position], view.measuredWidth, view.measuredHeight)
        }
        container.addView(view)

        view.setOnClickListener {
            onClickListener.onClick(position)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images.size
}