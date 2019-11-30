package com.yeonae.chamelezone.view.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Like
import kotlinx.android.synthetic.main.item_like.view.*

class PlaceReviewTabRvAdapter : RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {

    private var items = mutableListOf<Like>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(productResponse: Like)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceReviewViewHolder =
        PlaceReviewViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: PlaceReviewViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Like>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class PlaceReviewViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false)
    ) {
        fun bind(item: Like, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress
            }
        }
    }
}