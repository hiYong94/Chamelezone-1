package com.yeonae.chamelezone.view.mypage.mycourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Course
import kotlinx.android.synthetic.main.item_my_course.view.*

class MyCourseRvAdapter(private var items : ArrayList<Course>) : RecyclerView.Adapter<MyCourseRvAdapter.MyCourseViewHolder>() {

    //private var items = mutableListOf<Like>()
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(course: Course)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCourseViewHolder =
        MyCourseViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyCourseViewHolder, position: Int) =
        holder.bind(items[position], onClickListener)

    fun addData(addDataList: List<Course>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyCourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_course, parent, false)
    ) {
        fun bind(item: Course, listener: OnClickListener?) {
            itemView.run {
                setOnClickListener {
                    listener?.onClick(item)
                }
                tv_course_name.text = item.courseName
                tv_course_content.text = item.courseText
            }
        }
    }
}
