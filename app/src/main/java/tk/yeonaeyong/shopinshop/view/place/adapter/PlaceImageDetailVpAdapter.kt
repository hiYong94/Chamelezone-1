package tk.yeonaeyong.shopinshop.view.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slider_place_image.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.glideOriginImageSet

class PlaceImageDetailVpAdapter(private val images: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean =
        view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context)
                .inflate(R.layout.slider_place_image, container, false)

        view.post {
            images[position].let {
                view.iv_place.glideOriginImageSet(
                    it,
                    view.measuredWidth,
                    view.measuredHeight
                )
            }
        }
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.invalidate()

    override fun getCount(): Int =
        images.size
}