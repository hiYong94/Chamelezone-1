package com.yeonae.chamelezone.view.mypage.myplace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.item_my_place.view.*

class MyPlaceRvAdapter :
    RecyclerView.Adapter<MyPlaceRvAdapter.MyPlaceViewHolder>() {

    private val items = mutableListOf<PlaceItem>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var moreButtonListener: MoreButtonListener

    interface OnClickListener {
        fun onClick(place: PlaceItem)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(place: PlaceItem)
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

    fun addData(addDataList: List<PlaceItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    fun removeData(place: PlaceItem) {
        val position = items.indexOf(place)
        items.remove(place)
        notifyItemRemoved(position)
    }

    class MyPlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_place, parent, false)
    ) {
        fun bind(
            item: PlaceItem,
            clickListener: OnClickListener,
            moreButtonListener: MoreButtonListener
        ) {
            itemView.run {
                setOnClickListener {
                    clickListener.onClick(item)
                }
                tv_place_name.text = item.name
                tv_place_keyword.text = item.keyword
                tv_place_address.text = item.address
                iv_place_image.glideImageSet(
                    item.image,
                    iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )

                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(item)
                }
            }
        }
    }
}