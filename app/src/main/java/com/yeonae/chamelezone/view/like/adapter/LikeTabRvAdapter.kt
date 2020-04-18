package com.yeonae.chamelezone.view.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.LikeItem
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.item_like.view.*

class LikeTabRvAdapter :
    RecyclerView.Adapter<LikeTabRvAdapter.LikeViewHolder>() {

    private val items = mutableListOf<LikeItem>()
    private var onClickListener: OnClickListener? = null
    private var onLikeClickListener: OnLikeClickListener? = null

    interface OnClickListener {
        fun onClick(likeItem: LikeItem)
    }

    interface OnLikeClickListener {
        fun onLikeClick(likeItem: LikeItem)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun setOnLikeClickListener(listener: OnLikeClickListener) {
        onLikeClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder =
        LikeViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) =
        holder.bind(items[position], onClickListener, onLikeClickListener)

    fun addData(addDataList: List<LikeItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    fun removeData(place: LikeItem) {
        val position = items.indexOf(place)
        items.remove(place)
        notifyItemRemoved(position)
    }

    class LikeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
    ) {
        fun bind(
            item: LikeItem,
            listener: OnClickListener?,
            likeListener: OnLikeClickListener?
        ) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                iv_place_image.glideImageSet(
                    item.image,
                    iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )
                tv_place_name.text = item.name
                tv_place_keyword.text = item.keyword
                tv_place_address.text = item.address
                btn_like.isChecked = true
                btn_like.setOnClickListener {
                    likeListener?.onLikeClick(item)
                }
            }
        }
    }
}