package com.yeonae.chamelezone.view.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Course
import kotlinx.android.synthetic.main.item_course_list.view.*

class CourseTabRvAdapter(var courseList: ArrayList<Course>) :
    RecyclerView.Adapter<CourseTabRvAdapter.CourseViewHolder>() {

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(course: Course)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        CourseViewHolder(parent)

    override fun getItemCount(): Int =
        courseList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(courseList[position], onClickListener)

    fun addData(addDataList: List<Course>) {
        courseList.clear()
        courseList.addAll(addDataList)
        notifyDataSetChanged()
    }

    class CourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_course_list, parent, false)
    ) {
        fun bind(item: Course, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                Glide.with(itemView.context)
                    .load(
                        itemView.resources.getIdentifier(
                            item.courseImg,
                            "drawable",
                            itemView.context.packageName
                        )
                    )
                    .override(itemView.measuredWidth, itemView.measuredHeight)
                    .centerCrop()
                    .into(itemView.course_img)
                tv_course_name.text = item.courseName
                tv_register_date.text = item.registerDate
                tv_user_id.text = item.userId
            }
        }
    }
}