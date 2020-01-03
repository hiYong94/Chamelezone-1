package com.yeonae.chamelezone.view.mypage.mycourse.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Course
import kotlinx.android.synthetic.main.item_my_course.view.*

class MyCourseRvAdapter(private var items : ArrayList<Course>) : RecyclerView.Adapter<MyCourseRvAdapter.MyCourseViewHolder>() {

    //private var items = mutableListOf<Like>()
    private lateinit var onClickListener: OnClickListener
    private lateinit var locationListener: GetLocationListener

    interface OnClickListener {
        fun onClick(course: Course)
    }

    interface GetLocationListener {
        fun getLocation(x: Float, y: Int, position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    fun getLocation(listener: GetLocationListener){
        locationListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCourseViewHolder =
        MyCourseViewHolder(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: MyCourseViewHolder, position: Int) {
        if(::locationListener.isInitialized) {
            holder.bind(items[position], onClickListener, locationListener)
        }
    }

    fun addData(addDataList: List<Course>) {
        items.clear()
        items.addAll(addDataList)
        notifyDataSetChanged()
    }

    class MyCourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_my_course, parent, false)
    ) {
        fun bind(item: Course, clickListener: OnClickListener?, locationListener: GetLocationListener) {
            itemView.run {
                setOnClickListener {
                    clickListener?.onClick(item)
                }
                tv_course_name.text = item.courseName
                tv_course_content.text = item.courseText

                btn_more.setOnClickListener {
                    val originalPos = IntArray(2)
                    itemView.getLocationInWindow(originalPos)
                    val x = originalPos[0]
                    val y = originalPos[1]
                    val realX = layout_01.width + btn_more.x + btn_more.width
                    Log.d("tag", "$x & $y & $realX")
                    locationListener.getLocation(realX, y, layoutPosition)
                }
            }
        }
    }
}
