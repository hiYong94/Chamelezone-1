package com.yeonae.chamelezone.view.place.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideTransformations
import kotlinx.android.synthetic.main.item_place_review.view.*

open class PlaceReviewTabRvAdapter() :
    RecyclerView.Adapter<PlaceReviewTabRvAdapter.PlaceReviewViewHolder>() {
    private val reviewList = arrayListOf<ReviewItem>()
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var moreButtonListener: MoreButtonListener
    private var memberNumber = 0

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, review: ReviewItem)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(review: ReviewItem)
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
        fun bind(review: ReviewItem) {
            nickname.text = review.nickName
            reviewDate.text = review.regiDate
            reviewContent.text = review.content

            val reviewImages = review.images
            val imageList = arrayListOf<String>()
            reviewImages.forEachIndexed { index, _ ->
                imageList.add(IMAGE_RESOURCE + reviewImages[index])
            }

            if (imageList.size >= 2) {
                reviewCount.text = "+" + (imageList.size - 1)
                reviewImg.glideTransformations(
                    imageList.first(),
                    reviewImg.measuredWidth,
                    reviewImg.measuredHeight
                )
            } else {
                val backgroundColor =
                    ContextCompat.getDrawable(App.instance.context(), R.drawable.edge_rectangle_image)
                itemView.iv_image.background = backgroundColor
                reviewCount.text = ""
                itemView.post {
                    itemView.iv_image.layoutParams.width = 0
                    reviewImg.layoutParams = reviewImg.layoutParams.apply {
                        width = itemView.ll_image.measuredWidth
                    }
                    reviewImg.glideTransformations(
                        imageList.first(),
                        reviewImg.layoutParams.width,
                        reviewImg.measuredHeight
                    )
                }
            }

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
                if (memberNumber == 0)
                    btn_more.isInvisible = true
                btn_more.isVisible = memberNumber == review.memberNumber
                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(review)
                }
            }
        }
    }

    fun addDataList(addDataList: List<ReviewItem>) {
        reviewList.clear()
        reviewList.addAll(addDataList)
        notifyDataSetChanged()
    }

    fun replaceMore(memberNum: Int) {
        memberNumber = memberNum
        notifyItemRangeChanged(0, itemCount, PAYLOAD_ITEM_CHANGE)
    }

    fun removeData(review: ReviewItem) {
        val position = reviewList.indexOf(review)
        reviewList.remove(review)
        notifyItemRemoved(position)
    }

    companion object {
        const val PAYLOAD_ITEM_CHANGE = 1
    }

}