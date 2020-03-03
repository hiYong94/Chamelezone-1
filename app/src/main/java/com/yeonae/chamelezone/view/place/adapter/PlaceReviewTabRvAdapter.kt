package com.yeonae.chamelezone.view.place.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideTransformations
import com.yeonae.chamelezone.network.model.ReviewResponse
import kotlinx.android.synthetic.main.item_place_review.view.*

class PlaceReviewTabRvAdapter :
    RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {
    //    private lateinit var placeReviewList: ArrayList<ReviewResponse>
    private val reviewList = arrayListOf<ReviewResponse>()
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var moreButtonListener: MoreButtonListener
//    private lateinit var moreImageBtnListener: MoreImageBtnListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, review: ReviewResponse)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(review: ReviewResponse)
    }

    fun setItemClickListener(clickListener: OnItemClickListener) {
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
            holder.bind(reviewList[position])
        }
    }

    inner class PlaceReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nickname: TextView = itemView.findViewById(R.id.tv_nickname)
        private val reviewDate: TextView = itemView.findViewById(R.id.tv_review_date)
        private val reviewImg: ImageView = itemView.findViewById(R.id.iv_review_img)
        private val reviewContent: TextView = itemView.findViewById(R.id.tv_review_content)
        private val moreReviewImg: LinearLayout = itemView.findViewById(R.id.iv_image)
        private val reviewCount: TextView = itemView.findViewById(R.id.tv_image_count)

        @SuppressLint("SetTextI18n")
        fun bind(review: ReviewResponse) {
            nickname.text = review.nickName
            reviewDate.text = review.regiDate
            reviewContent.text = review.content
            val images = review.savedImageName.split(",")
            Log.d("imageList images", images.toString())
            val imageList = images.map {
                IMAGE_URL + it
            }
//            images.forEachIndexed { index, _ ->
//                imageList.add("http://13.209.136.122:3000/image/" + images[index])
//                Log.d("imageList review", images[index])
//            }
            reviewImg.glideTransformations(imageList[0], reviewImg.measuredWidth, reviewImg.measuredHeight)
            reviewCount.text = "+" + (imageList.size - 1)

            reviewImg.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = reviewList[position]
                    itemClickListener.onItemClick(itemView, position, review)
                    reviewList[position] = review
                }
            }

            moreReviewImg.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    if (::itemClickListener.isInitialized) {
                        itemClickListener.onItemClick(itemView, position, review)
                    }
                    reviewList[position] = review
                }
            }

            itemView.apply {
                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(review)
                }
            }
        }
    }

    fun addData(addDataList: List<ReviewResponse>) {
        reviewList.clear()
        reviewList.addAll(addDataList)
        notifyDataSetChanged()
    }

    companion object {
        const val IMAGE_URL = "http://13.209.136.122:3000/image/"
    }
}