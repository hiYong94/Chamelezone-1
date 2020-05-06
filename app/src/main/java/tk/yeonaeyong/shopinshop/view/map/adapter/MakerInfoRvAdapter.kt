package tk.yeonaeyong.shopinshop.view.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_marker_info.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.MapItem
import tk.yeonaeyong.shopinshop.ext.glideImageSet

class MakerInfoRvAdapter : RecyclerView.Adapter<MakerInfoRvAdapter.SearchViewHolder>() {
    private val items = mutableListOf<MapItem>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(mapItem: MapItem)
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

    fun addData(addDataList: List<MapItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_marker_info, parent, false)
    ) {
        fun bind(item: MapItem, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
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