package com.yeonae.chamelezone.view.mypage.myreview.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review
import com.yeonae.chamelezone.view.review.MyReviewDetailActivity
import kotlinx.android.synthetic.main.item_my_review.view.*

class MyReviewRvAdapter(private var items: ArrayList<Review>) :
    RecyclerView.Adapter<MyReviewRvAdapter.MyReviewViewHolder>() {

//    private var items = mutableListOf<Review>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var locationListener: GetLocationListener

    interface OnClickListener {
        fun onClick(review: Review)
    }

    interface GetLocationListener {
        fun getLocation(x: Float, y: Int, position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun getLocation(listener: GetLocationListener){
        locationListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder =
        MyReviewViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        if(::locationListener.isInitialized) {
            holder.bind(items[position], onClickListener, locationListener)
        }
    }

    fun addData(addDataList: List<Review>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyReviewViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_review, parent, false)
    ) {
        fun bind(item: Review, clickListener: OnClickListener?, locationListener: GetLocationListener) {
            itemView.run {
                setOnClickListener {
                    clickListener?.onClick(item)
                    
                    val intent = Intent(itemView.context, MyReviewDetailActivity::class.java)
                    itemView.context.startActivity(intent)
                }
                tv_place_name.text = item.placeName
                tv_review_content.text = item.reviewContent

                btn_more.setOnClickListener {
                    val originalPos = IntArray(2)
                    itemView.getLocationInWindow(originalPos)
                    val x = originalPos[0]
                    val y = originalPos[1]
                    val realX = layout_01.width + btn_more.x + btn_more.width
                    Log.d("tag", "$x & $y & $realX")
                    locationListener.getLocation(realX, y, layoutPosition)
                }
            }
        }
    }
}
