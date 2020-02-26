package com.yeonae.chamelezone.view.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.CourseItem
import com.yeonae.chamelezone.ext.glideImageSet
import kotlinx.android.synthetic.main.item_course.view.*

class CourseTabRvAdapter :
    RecyclerView.Adapter<CourseTabRvAdapter.CourseViewHolder>() {
    private val items = mutableListOf<CourseItem>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(course: CourseItem)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        CourseViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<CourseItem>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class CourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
    ) {
        fun bind(item: CourseItem, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_course_title.text = item.title
                tv_register_date.text = item.regiDate
                tv_user_nickname.text = item.nickName
                iv_course_image.glideImageSet(
                    item.savedImageName,
                    iv_course_image.measuredWidth,
                    iv_course_image.measuredHeight
                )
            }
        }
    }
}