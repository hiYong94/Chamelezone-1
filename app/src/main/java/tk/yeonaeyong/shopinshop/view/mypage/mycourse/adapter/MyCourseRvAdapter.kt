package tk.yeonaeyong.shopinshop.view.mypage.mycourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_my_course.view.*
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.MyCourseItem
import tk.yeonaeyong.shopinshop.ext.glideImageSet

class MyCourseRvAdapter :
    RecyclerView.Adapter<MyCourseRvAdapter.MyCourseViewHolder>() {

    private var items = mutableListOf<MyCourseItem>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var moreButtonListener: MoreButtonListener

    interface OnClickListener {
        fun onClick(course: MyCourseItem)
    }

    interface MoreButtonListener {
        fun bottomSheetDialog(course: MyCourseItem)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun setMoreButtonListener(listener: MoreButtonListener) {
        moreButtonListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCourseViewHolder =
        MyCourseViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyCourseViewHolder, position: Int) {
        if (::moreButtonListener.isInitialized) {
            holder.bind(items[position], onClickListener, moreButtonListener)
        }
    }

    fun addData(addDataList: List<MyCourseItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    fun removeData(course: MyCourseItem) {
        val position = items.indexOf(course)
        items.remove(course)
        notifyItemRemoved(position)
    }

    class MyCourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_course, parent, false)
    ) {
        fun bind(
            item: MyCourseItem,
            clickListener: OnClickListener,
            moreButtonListener: MoreButtonListener
        ) {
            itemView.run {
                setOnClickListener {
                    clickListener.onClick(item)
                }
                tv_course_name.text = item.title
                tv_course_content.text = item.content
                iv_course_image.glideImageSet(
                    item.savedImageName,
                    iv_course_image.measuredWidth,
                    iv_course_image.measuredHeight
                )

                btn_more.setOnClickListener {
                    moreButtonListener.bottomSheetDialog(item)
                }
            }
        }
    }
}
