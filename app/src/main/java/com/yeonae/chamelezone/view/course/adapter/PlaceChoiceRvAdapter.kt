package com.yeonae.chamelezone.view.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.item_place_choice.view.*

class PlaceChoiceRvAdapter(var items: ArrayList<Place>) :
    RecyclerView.Adapter<PlaceChoiceRvAdapter.PlaceChoiceViewHolder>() {
    private var selectedPosition = -1
    //private var items = mutableListOf<Course>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(place: Place)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceChoiceViewHolder =
        PlaceChoiceViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: PlaceChoiceViewHolder, position: Int) {
        holder.bind(items[position], onClickListener)
        holder.itemView.btn_check.isChecked = selectedPosition == position

        holder.itemView.btn_check.setOnClickListener {
            if (selectedPosition == position) {
                holder.itemView.btn_check.isChecked = false
            } else {
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    fun addData(addDataList: List<Place>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class PlaceChoiceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_place_choice, parent, false)
    ) {
        fun bind(item: Place, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress
            }
        }
    }
}