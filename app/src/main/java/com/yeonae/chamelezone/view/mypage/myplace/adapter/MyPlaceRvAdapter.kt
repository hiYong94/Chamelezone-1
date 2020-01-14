package com.yeonae.chamelezone.view.mypage.myplace.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.activity_my_place.view.*
import kotlinx.android.synthetic.main.item_my_place.view.*

class MyPlaceRvAdapter(private var items: ArrayList<Place>) :
    RecyclerView.Adapter<MyPlaceRvAdapter.MyPlaceViewHolder>() {

    //private var items = mutableListOf<Place>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var locationListener: GetLocationListener

    interface OnClickListener {
        fun onClick(place: Place)
    }

    interface GetLocationListener {
        fun getLocation()
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun getLocation(listener: GetLocationListener){
        locationListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlaceViewHolder =
        MyPlaceViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyPlaceViewHolder, position: Int) {
        if(::locationListener.isInitialized) {
            holder.bind(items[position], onClickListener, locationListener)
        }
    }

    fun addData(addDataList: List<Place>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyPlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_place, parent, false)
    ) {
        fun bind(item: Place, clickListener: OnClickListener?, locationListener: GetLocationListener) {
            itemView.run {
                setOnClickListener {
                    clickListener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress

                btn_more.setOnClickListener {
//                    val originalPos = IntArray(2)
//                    itemView.getLocationInWindow(originalPos)
//                    val x = originalPos[0]
//                    val y = originalPos[1]
//                    val realX = layout_01.width + btn_more.x + btn_more.width
//                    Log.d("tag", "$x & $y & $realX")
                    locationListener.getLocation()
                }
            }
        }
    }
}