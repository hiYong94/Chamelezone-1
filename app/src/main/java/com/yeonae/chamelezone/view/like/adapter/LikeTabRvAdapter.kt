package com.yeonae.chamelezone.view.like.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.item_like.view.*

class LikeTabRvAdapter :
    RecyclerView.Adapter<LikeTabRvAdapter.LikeViewHolder>() {

    private val items = mutableListOf<PlaceResponse>()
    private var onClickListener: OnClickListener? = null
    private var onLikeClickListener: OnLikeClickListener? = null

    interface OnClickListener {
        fun onClick(place: PlaceResponse)
    }

    interface OnLikeClickListener {
        fun onLikeClick(place: PlaceResponse)
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

    fun addData(addDataList: List<PlaceResponse>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class LikeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
    ) {
        fun bind(
            item: PlaceResponse,
            listener: OnClickListener?,
            likeListener: OnLikeClickListener?
        ) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                val image = IMAGE_RESOURCE + item.savedImageName[0]
                iv_place_image.glideImageSet(
                    image,
                    iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )
                tv_place_name.text = item.name
                item.keywordName.forEach {
                    if (it == item.keywordName[0]) {
                        tv_place_keyword.text = it
                    } else {
                        tv_place_keyword.text = "${tv_place_keyword.text}${","} $it"
                    }
                }
                tv_place_address.text = item.address
                btn_like.isChecked = true
                btn_like.setOnClickListener {
                    likeListener?.onLikeClick(item)
                }
            }
        }
    }
}