package com.yeonae.chamelezone.view.mypage.myplace.adapter

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.activity_place_detail.view.*
import kotlinx.android.synthetic.main.item_my_place.view.*

class MyPlaceRvAdapter(private var items : ArrayList<Place>) : RecyclerView.Adapter<MyPlaceRvAdapter.MyPlaceViewHolder>() {

    //private var items = mutableListOf<Place>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(place: Place)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPlaceViewHolder =
        MyPlaceViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyPlaceViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Place>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyPlaceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_place, parent, false)
    ) {
        fun bind(item: Place, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress
                btn_more.setOnClickListener {
                    showPopup(btn_more)
                }

            }
        }
        private fun showPopup(v: View) {
            val popup = PopupMenu(v.context, v)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.show()
        }
    }
}