package com.yeonae.chamelezone.view.mypage.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Review
import kotlinx.android.synthetic.main.item_my_review.view.*

class MyReviewRvAdapter(private var items: ArrayList<Review>) :
    RecyclerView.Adapter<MyReviewRvAdapter.MyReviewViewHolder>() {

    //private var items = mutableListOf<Review>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(review: Review)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder =
        MyReviewViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Review>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyReviewViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_review, parent, false)
    ) {
        fun bind(item: Review, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_place_name.text = item.placeName
                tv_review_content.text = item.reviewContent
            }
        }
    }
}
