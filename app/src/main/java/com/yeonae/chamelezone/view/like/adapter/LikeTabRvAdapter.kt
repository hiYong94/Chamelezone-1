package com.yeonae.chamelezone.view.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.item_like.view.*

class LikeTabRvAdapter(private var items : ArrayList<Place>) : RecyclerView.Adapter<LikeTabRvAdapter.LikeViewHolder>() {

    //private var items = mutableListOf<Place>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(place: Place)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder =
        LikeViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Place>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class LikeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
    ) {
        fun bind(item: Place, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                Glide.with(itemView.context)
                    .load(
                        itemView.resources.getIdentifier(
                            item.placeImg,
                            "drawable",
                            itemView.context.packageName
                        )
                    )
                    .override(itemView.measuredWidth, itemView.measuredHeight)
                    .centerCrop()
                    .into(itemView.like_img)
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress
            }
        }
    }
}