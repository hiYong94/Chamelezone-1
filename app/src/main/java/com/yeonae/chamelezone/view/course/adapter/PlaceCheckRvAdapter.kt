package com.yeonae.chamelezone.view.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.item_place_check.view.*

class PlaceCheckRvAdapter :
    RecyclerView.Adapter<PlaceCheckRvAdapter.PlaceChoiceViewHolder>() {
    private var selectedPosition = -1
    private val items = mutableListOf<PlaceItem>()
    private var onClickListener: OnClickListener? = null
    private var onCheckButtonListener: OnCheckButtonListener? = null

    interface OnClickListener {
        fun onClick(place: PlaceItem, isChecked: Boolean)
    }

    interface OnCheckButtonListener {
        fun onClick(place: PlaceItem, isChecked: Boolean)
    }

    fun setOnClickListener(
        clickListener: OnClickListener,
        checkButtonListener: OnCheckButtonListener
    ) {
        onClickListener = clickListener
        onCheckButtonListener = checkButtonListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceChoiceViewHolder =
        PlaceChoiceViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: PlaceChoiceViewHolder, position: Int) {
        holder.bind(position, items[position], onClickListener)
    }

    fun addData(addDataList: List<PlaceItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
        selectedPosition = -1
    }

    inner class PlaceChoiceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_place_check, parent, false)
    ) {
        fun bind(position: Int, item: PlaceItem, listener: OnClickListener?) {
            itemView.run {
                btn_check.isChecked = selectedPosition == position
                btn_check.setOnClickListener {
                    if (selectedPosition == position) {
                        listener?.onClick(item, false)
                        btn_check.isChecked = false
                        selectedPosition = -1
                    } else {
                        listener?.onClick(item, true)
                        selectedPosition = position
                        notifyDataSetChanged()
                    }
                }
                setOnClickListener {
                    if (selectedPosition == position) {
                        listener?.onClick(item, false)
                        btn_check.isChecked = false
                        selectedPosition = -1
                    } else {
                        listener?.onClick(item, true)
                        selectedPosition = position
                        notifyDataSetChanged()
                    }
                }
                tv_place_name.text = item.name
                tv_place_keyword.text = item.keyword
                tv_place_address.text = item.address
                iv_place_image.glideImageSet(
                    item.image, iv_place_image.measuredWidth,
                    iv_place_image.measuredHeight
                )
            }
        }
    }
}