package com.yeonae.chamelezone.view.mypage.myplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.item_my_place.view.*

class MyPlaceRvAdapter :
    RecyclerView.Adapter<MyPlaceRvAdapter.MyPlaceViewHolder>() {

    private val items = mutableListOf<PlaceResponse>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var moreButtonListener: MoreButtonListener

    interface OnClickListener {
        fun onClick(place: PlaceResponse)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog()
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun setMoreButtonListener(listener: MoreButtonListener) {
        moreButtonListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlaceViewHolder =
        MyPlaceViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyPlaceViewHolder, position: Int) {
        if (::moreButtonListener.isInitialized) {
            holder.bind(items[position], onClickListener, moreButtonListener)
        }
    }

    fun addData(addDataList: List<PlaceResponse>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyPlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_place, parent, false)
    ) {
        fun bind(
            item: PlaceResponse,
            clickListener: OnClickListener,
            moreButtonListener: MoreButtonListener
        ) {
            itemView.run {
                setOnClickListener {
                    clickListener.onClick(item)
                }
                tv_place_name.text = item.name
                item.keywordName.forEach {
                    if (it == item.keywordName[0]) {
                        tv_place_keyword.text = it
                    } else {
                        tv_place_keyword.text = "${tv_place_keyword.text}${","} $it"
                    }
                }
                tv_place_address.text = item.address
                val image = IMAGE_RESOURCE + item.savedImageName[0]
                iv_place_image.glideImageSet(
                    image,
                    iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )

                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog()
                }
            }
        }

        companion object {
            private const val IMAGE_RESOURCE = "http://13.209.136.122:3000/image/"
        }

    }
}