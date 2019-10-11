package com.yeonae.chamelezone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (var context: Context, var placeList: ArrayList<Place>) :
RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeImg = itemView?.findViewById<ImageView>(R.id.place_img)
        private val placeName = itemView?.findViewById<TextView>(R.id.place_name)
        private val distance = itemView?.findViewById<TextView>(R.id.distance)
        private val keyword = itemView?.findViewById<TextView>(R.id.keyword)

        fun bind(place: Place, context: Context) {
            if (place.placeImg != "") {
                val resourceId = context.resources.getIdentifier(
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(placeList[position], context)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}