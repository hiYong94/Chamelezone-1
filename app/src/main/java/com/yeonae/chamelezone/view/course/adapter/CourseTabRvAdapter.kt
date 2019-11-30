package com.yeonae.chamelezone.view.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Course
import kotlinx.android.synthetic.main.item_course_list.view.*
import kotlinx.android.synthetic.main.item_my_course.view.tv_course_name
import kotlinx.android.synthetic.main.item_place_review.view.tv_user_id

class CourseTabRvAdapter(var items : ArrayList<Course>) : RecyclerView.Adapter<CourseTabRvAdapter.CourseViewHolder>() {

    //private var items = mutableListOf<Course>()
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
        items.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Course>) {
        items.clear()
        items.addAll(addDataList)
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
                tv_course_name.text = item.courseName
                tv_register_date.text = item.registerDate
                tv_user_id.text = item.userId
            }
        }
    }
}