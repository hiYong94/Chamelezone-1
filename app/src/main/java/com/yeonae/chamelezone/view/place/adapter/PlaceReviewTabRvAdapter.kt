package com.yeonae.chamelezone.view.place.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review
import com.yeonae.chamelezone.view.review.MyReviewDetailActivity
import kotlinx.android.synthetic.main.item_place_review.view.*

class PlaceReviewTabRvAdapter(private val reviewList: ArrayList<Review>) :
    RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {

//    private var onClickListener: OnClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }


    class PlaceReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeReviewList: ArrayList<Review>? = null
        private val itemClickListener: OnItemClickListener? = null
        private val userId = itemView.tv_user_id
        private val reviewDate = itemView.tv_review_date
        private val reviewImg = itemView.iv_review_img
        private val reviewContent = itemView.tv_review_content

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

                    val intent = Intent(itemView.context, MyReviewDetailActivity::class.java)
                    itemView.context.startActivity(intent)
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