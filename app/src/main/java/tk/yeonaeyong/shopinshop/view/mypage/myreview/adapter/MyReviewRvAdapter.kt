package tk.yeonaeyong.shopinshop.view.mypage.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_my_review.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.ReviewItem
import tk.yeonaeyong.shopinshop.ext.glideImageSet

class MyReviewRvAdapter :
    RecyclerView.Adapter<MyReviewRvAdapter.MyReviewViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    private lateinit var moreButtonListener: MoreButtonListener
    private val items = ArrayList<ReviewItem>()

    interface OnClickListener {
        fun onClick(review: ReviewItem)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(review: ReviewItem)
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

    fun addDataList(addDataList: List<ReviewItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    fun removeData(review: ReviewItem) {
        val position = items.indexOf(review)
        items.remove(review)
        notifyItemRemoved(position)
    }

    class MyReviewViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_review, parent, false)
    ) {
        private val placeImg = itemView.iv_review_img
        private val placeName = itemView.tv_place_name
        private val content = itemView.tv_review_content
        private val regiDate = itemView.tv_review_date
        fun bind(
            review: ReviewItem,
            clickListener: OnClickListener,
            moreButtonListener: MoreButtonListener
        ) {
            itemView.run {
                setOnClickListener {
                    clickListener.onClick(review)
                }
                placeName.text = review.name
                content.text = review.content
                regiDate.text = review.regiDate

                val reviewImages = review.image

                placeImg.glideImageSet(
                    reviewImages,
                    placeImg.measuredWidth,
                    placeImg.measuredHeight
                )

                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(review)
                }
            }
        }
    }
}