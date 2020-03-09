package com.yeonae.chamelezone.view.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.item_place.view.*

class HomePlaceRvAdapter :
    RecyclerView.Adapter<HomePlaceRvAdapter.Holder>() {
    private val placeList = arrayListOf<PlaceResponse>()
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(place: PlaceResponse)
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
        itemClickListener = clickListener
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeImg = itemView.place_img
        private val placeName = itemView.place_name
        private val keyword = itemView.keyword

        fun bind(place: PlaceResponse, listener: OnItemClickListener) {
            placeName.text = place.name
            keyword.text = place.keywordName
            val images = place.savedImageName.split(",")
            val imageList = arrayListOf<String>()
            images.forEachIndexed { index, _ ->
                imageList.add(IMAGE_RESOURCE + images[index])
            }

            Log.d("imageList", images.toString())
            Log.d("imageList", imageList.toString())

            placeImg.glideImageSet(imageList[0], placeImg.measuredWidth, placeImg.measuredHeight)

            itemView.setOnClickListener {
                listener.onItemClick(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(placeList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun addData(addDataList: List<PlaceResponse>) {
        placeList.clear()
        placeList.addAll(addDataList)
        notifyDataSetChanged()
    }
}