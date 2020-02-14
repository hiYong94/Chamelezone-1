package com.yeonae.chamelezone.view.course.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.CourseResponse
import kotlinx.android.synthetic.main.item_course_list.view.*

class CourseTabRvAdapter() :
    RecyclerView.Adapter<CourseTabRvAdapter.CourseViewHolder>() {
    private val items = mutableListOf<CourseResponse>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(course: CourseResponse)
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

    fun addData(addDataList: List<CourseResponse>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class CourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_course_list, parent, false)
    ) {
        fun bind(item: CourseResponse, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                val courseItem = item.toCourseItem(item)
                tv_course_title.text = courseItem.title
                tv_register_date.text = courseItem.regiDate
                tv_user_nickname.text = courseItem.nickName
                iv_course_image.glideImageSet(
                    courseItem.savedImageName,
                    itemView.measuredWidth,
                    itemView.measuredHeight
                )
            }
        }

        companion object {
            private const val IMAGE_RESOURCE = "http://13.209.136.122:3000/image/"
        }
    }
}