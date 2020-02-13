package com.yeonae.chamelezone.view.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.item_search.view.*

class SearchRvAdapter : RecyclerView.Adapter<SearchRvAdapter.SearchViewHolder>() {

    private var items = mutableListOf<PlaceResponse>()
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
        LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
    ) {
        fun bind(item: PlaceResponse, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.name
                tv_place_keyword.text = item.keywordName.replace(",", ", ")
                tv_place_address.text = item.address
                val placeImages = item.savedImageName.split(",")
                val images = arrayListOf<String>()
                for(i in placeImages.indices){
                    images.add("http://13.209.136.122:3000/image/"+ placeImages[i])
                }
                iv_place_image.glideImageSet(images[0], 80, 80)
            }
        }
    }
}