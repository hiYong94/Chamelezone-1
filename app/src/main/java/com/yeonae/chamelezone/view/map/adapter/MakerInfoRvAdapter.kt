package com.yeonae.chamelezone.view.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.item_marker_info.view.*

class MakerInfoRvAdapter : RecyclerView.Adapter<MakerInfoRvAdapter.SearchViewHolder>() {
    private val items = mutableListOf<PlaceResponse>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(place: PlaceResponse)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<PlaceResponse>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_marker_info, parent, false)
    ) {
        fun bind(item: PlaceResponse, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.name
                item.keywordName.forEach {
                    if (it == item.keywordName[0]) {
                        tv_place_keyword.text = it
                    } else {
                        tv_place_keyword.text = "${tv_place_keyword.text}, $it"
                    }
                }
                tv_place_address.text = item.address
                val image = IMAGE_RESOURCE + item.savedImageName[0]
                iv_place_image.glideImageSet(
                    image, iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )
            }
        }
    }
}