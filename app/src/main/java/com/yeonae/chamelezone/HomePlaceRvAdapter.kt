package com.yeonae.chamelezone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.model.Place
import kotlinx.android.synthetic.main.item_place_list.view.*

class HomePlaceRvAdapter(var placeList: ArrayList<Place>) :
    RecyclerView.Adapter<HomePlaceRvAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeList: ArrayList<Place>? = null
        private val itemClickListener: OnItemClickListener? = null
        private val placeImg = itemView.place_img
        private val placeName = itemView.place_name
        private val distance = itemView.distance
        private val keyword = itemView.keyword

        fun bind(place: Place) {
            if (place.placeImg.isNotEmpty()) {
                val resourceId = itemView.resources.getIdentifier(
                    place.placeImg,
                    "drawable",
                    itemView.context.packageName
                )
                placeImg.setImageResource(resourceId)
            } else {
                placeImg.setImageResource(R.mipmap.ic_launcher)
            }
            placeName.text = place.placeName
            distance.text = place.distance
            keyword.text = place.keyword

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = placeList?.get(position)
                    itemClickListener?.onItemClick(itemView, position)
                    placeList?.set(position, place)

                    val intent = Intent(itemView.context, PlaceDetailActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(placeList[position])
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}