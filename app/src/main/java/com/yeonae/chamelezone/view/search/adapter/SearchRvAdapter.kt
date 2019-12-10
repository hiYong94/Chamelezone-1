package com.yeonae.chamelezone.view.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.item_like.view.tv_place_address
import kotlinx.android.synthetic.main.item_like.view.tv_place_keyword
import kotlinx.android.synthetic.main.item_like.view.tv_place_name
import kotlinx.android.synthetic.main.item_search.view.*

class SearchRvAdapter(var items : ArrayList<Place>) : RecyclerView.Adapter<SearchRvAdapter.SearchViewHolder>() {

    //private var items = mutableListOf<Place>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(like: Place)
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

    fun addData(addDataList: List<Place>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
    ) {
        fun bind(item: Place, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_place_keyword.text = item.placeKeyword
                tv_place_address.text = item.placeAddress
                tv_place_distance.text = item.placeDistance
            }
        }
    }
}