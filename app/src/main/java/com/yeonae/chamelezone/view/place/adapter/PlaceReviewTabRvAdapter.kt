package com.yeonae.chamelezone.view.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review

class PlaceReviewTabRvAdapter(private val reviewList: ArrayList<Review>) :
    RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {
    private lateinit var placeReviewList: ArrayList<Review>
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class PlaceReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userId: TextView = itemView.findViewById(R.id.tv_user_id)
        private val reviewDate: TextView = itemView.findViewById(R.id.tv_review_date)
        private val reviewImg: ImageView = itemView.findViewById(R.id.iv_review_img)
        private val reviewContent: TextView = itemView.findViewById(R.id.tv_review_content)

        fun bind(review: Review) {
            if (review.reviewImage.isNotEmpty()) {
                val resourceId = itemView.resources.getIdentifier(
                    review.reviewImage,
                    "drawable",
                    itemView.context.packageName
                )
                reviewImg.setImageResource(resourceId)
            } else {
                reviewImg.setImageResource(R.mipmap.ic_launcher)
            }
            userId.text = review.userId
            reviewDate.text = review.reviewDate
            reviewContent.text = review.reviewContent

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = placeReviewList?.get(position)
                    itemClickListener?.onItemClick(itemView, position)
                    placeReviewList?.set(position, review)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false)

        return PlaceReviewViewHolder(view)
    }

    override fun getItemCount(): Int =
        reviewList.size

    override fun onBindViewHolder(holder: PlaceReviewViewHolder, position: Int) =
        holder.bind(reviewList[position])

    fun addData(addDataList: List<Review>) {
        reviewList.clear()
        reviewList.addAll(addDataList)
        notifyDataSetChanged()
    }

}