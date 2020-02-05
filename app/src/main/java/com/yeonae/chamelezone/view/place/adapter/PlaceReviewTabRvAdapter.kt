package com.yeonae.chamelezone.view.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Review
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.item_place_review.view.*

class PlaceReviewTabRvAdapter(private val reviewList: ArrayList<Review>) :
    RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {
    private lateinit var placeReviewList: ArrayList<Review>
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var moreButtonListener: MoreButtonListener
//    private lateinit var moreImageBtnListener: MoreImageBtnListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog()
    }

    fun setItemClickListener(clickListener: OnItemClickListener){
        itemClickListener = clickListener
    }

    fun setMoreButtonListener(listener: MoreButtonListener) {
        moreButtonListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false)

        return PlaceReviewViewHolder(view)
    }

    override fun getItemCount(): Int =
        reviewList.size

    override fun onBindViewHolder(holder: PlaceReviewViewHolder, position: Int) {
        if (::moreButtonListener.isInitialized) {
            holder.bind(reviewList[position], moreButtonListener)
        }
    }

    fun addData(addDataList: List<Review>) {
        reviewList.clear()
        reviewList.addAll(addDataList)
        notifyDataSetChanged()
    }

    inner class PlaceReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userId: TextView = itemView.findViewById(R.id.tv_user_id)
        private val reviewDate: TextView = itemView.findViewById(R.id.tv_review_date)
        private val reviewImg: ImageView = itemView.findViewById(R.id.iv_review_img)
        private val reviewContent: TextView = itemView.findViewById(R.id.tv_review_content)
        private val moreReviewImg: LinearLayout = itemView.findViewById(R.id.iv_image)

        fun bind(review: Review, moreButtonListener: MoreButtonListener) {
            if (review.reviewImage.isNotEmpty()) {
                val resourceId = itemView.resources.getIdentifier(
                    review.reviewImage,
                    "drawable",
                    itemView.context.packageName
                )
                reviewImg.glideImageSet(resourceId, reviewImg.measuredWidth, reviewImg.measuredHeight)
            } else {
                reviewImg.setImageResource(R.mipmap.ic_launcher)
            }
            userId.text = review.userId
            reviewDate.text = review.reviewDate
            reviewContent.text = review.reviewContent

            reviewImg.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    if(::itemClickListener.isInitialized){
                        itemClickListener.onItemClick(itemView, position)
                    }
                    if (::placeReviewList.isInitialized){
                        placeReviewList[position] = review
                    }
                }
            }
            moreReviewImg.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    if(::itemClickListener.isInitialized){
                        itemClickListener.onItemClick(itemView, position)
                    }
                    if (::placeReviewList.isInitialized){
                        placeReviewList[position] = review
                    }
                }
            }
            itemView.apply {
                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog()
                }
            }

        }
    }
}