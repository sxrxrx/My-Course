package com.sxrxrx.coursescheduleremake.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course
        val tvCourse: TextView = itemView.findViewById(R.id.tv_course)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvLecturer: TextView = itemView.findViewById(R.id.tv_lecturer)

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)
            tvCourse.text = courseName
            tvTime.text = timeFormat
            tvLecturer.text = lecturer
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}