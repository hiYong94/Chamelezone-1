package com.yeonae.chamelezone.view.mypage.myreview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.ReviewResponse
import kotlinx.android.synthetic.main.item_my_review.view.*

class MyReviewRvAdapter :
    RecyclerView.Adapter<MyReviewRvAdapter.MyReviewViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    private lateinit var moreButtonListener: MoreButtonListener
    private var items = ArrayList<ReviewResponse>()

    interface OnClickListener {
        fun onClick(review: ReviewResponse)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(review: ReviewResponse)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun setMoreButtonListener(listener: MoreButtonListener) {
        moreButtonListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder =
        MyReviewViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        if (::moreButtonListener.isInitialized) {
            holder.bind(items[position], onClickListener, moreButtonListener)
        }
    }

    fun addData(addDataList: List<ReviewResponse>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyReviewViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_review, parent, false)
    ) {
        private val placeImg = itemView.iv_review_img
        private val placeName = itemView.tv_place_name
        private val content = itemView.tv_review_content
        private val regiDate = itemView.tv_review_date
        fun bind(
            review: ReviewResponse,
            clickListener: OnClickListener,
            moreButtonListener: MoreButtonListener
        ) {
            itemView.run {
                setOnClickListener {
                    clickListener.onClick(review)
                }
                val dateSplit = review.regiDate.split("T")
                placeName.text = review.name
                content.text = review.content
                regiDate.text = dateSplit[0]
                val images = review.savedImageName.split(",")
                val imageList = images.map {
                    IMAGE_RESOURCE + it
                }
                imageList.let {
                    placeImg.glideImageSet(imageList[0], placeImg.measuredWidth, placeImg.measuredHeight)
                    Log.d("imageList", images.toString())
                    Log.d("imageList", imageList.toString())
                }

                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(review)
                }
            }
        }
    }
}