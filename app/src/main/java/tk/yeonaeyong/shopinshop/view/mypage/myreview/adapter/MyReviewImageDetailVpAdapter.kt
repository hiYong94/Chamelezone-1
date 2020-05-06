package tk.yeonaeyong.shopinshop.view.mypage.myreview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slider_item_myreview_image_detail.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.glideOriginImageSet

class MyReviewImageDetailVpAdapter(private val images: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slider_item_myreview_image_detail, container, false)

        view.post {
            view.iv_review_img.glideOriginImageSet(
                images[position],
                view.measuredWidth,
                view.measuredHeight
            )
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images.size
}