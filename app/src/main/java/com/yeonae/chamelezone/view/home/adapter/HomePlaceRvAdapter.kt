package com.yeonae.chamelezone.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.util.Logger
import com.yeonae.chamelezone.util.distanceByDegree
import kotlinx.android.synthetic.main.item_place.view.*

class HomePlaceRvAdapter(private val currentLatitude: Double, private val currentLongitude: Double) :
    RecyclerView.Adapter<HomePlaceRvAdapter.Holder>() {
    private val placeList = arrayListOf<PlaceResponse>()
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var likeButtonListener: LikeButtonListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, place: PlaceResponse)
    }

    interface LikeButtonListener {
        fun onLikeClick(placeResponse: PlaceResponse, isChecked: Boolean)
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
        itemClickListener = clickListener
    }

    fun setLikeButtonListener(listener: LikeButtonListener) {
        likeButtonListener = listener
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeImg = itemView.place_img
        private val placeName = itemView.place_name
        private val keyword = itemView.keyword
        private val like = itemView.btn_like

        fun bind(place: PlaceResponse) {
            Logger.d("HomePlaceTabFragment placeLatitude ${place.latitude}")
            Logger.d("HomePlaceTabFragment placeLongitude ${place.longitude}")
            val latitude = place.latitude.toDouble()
            val longitude = place.longitude.toDouble()

            placeName.text = place.name
            place.keywordName.forEach {
                if (it == place.keywordName[0]) {
                    keyword.text = it
                } else {
                    keyword.text = "${keyword.text}, $it"
                }
            }
            if (place.savedImageName.isNotEmpty()) {
                val image = IMAGE_RESOURCE + place.savedImageName[0]
                placeImg.glideImageSet(image, placeImg.measuredWidth, placeImg.measuredHeight)
            }

            itemView.setOnClickListener {
                itemClickListener.onItemClick(itemView, adapterPosition, place)
            }

            itemView.apply {
                btn_like.isChecked = place.likeStatus
                btn_like.setOnClickListener {
                    likeButtonListener.onLikeClick(place, btn_like.isChecked)
                }

                Logger.d("HomePlaceTabFragment currentLatitude $currentLatitude")
                Logger.d("HomePlaceTabFragment currentLongitude $currentLongitude")
                val distanceCalculator = distanceByDegree(currentLatitude, currentLongitude, latitude, longitude)

                Logger.d("HomePlaceTabFragment distanceCalculator $distanceCalculator")

                distance.text = distanceCalculator
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(placeList[position])
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