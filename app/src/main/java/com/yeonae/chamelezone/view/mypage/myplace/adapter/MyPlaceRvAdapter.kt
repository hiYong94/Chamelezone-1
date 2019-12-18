package com.yeonae.chamelezone.view.mypage.myplace.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.item_my_place.view.*

class MyPlaceRvAdapter(private var items: ArrayList<Place>) :
    RecyclerView.Adapter<MyPlaceRvAdapter.MyPlaceViewHolder>() {

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

                btn_more.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                        if (p1?.action == MotionEvent.ACTION_DOWN) {
                            popup_menu.visibility = View.VISIBLE
                            popup_menu.x = p1.x
                            popup_menu.y = p1.y
                        }
                        return true
                    }
                })
            }
        }

        private fun showPopup(v: View) {
            val popup = PopupMenu(v.context, v)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.show()
        }
    }
}