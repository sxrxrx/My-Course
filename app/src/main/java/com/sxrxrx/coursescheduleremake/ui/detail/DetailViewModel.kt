package com.sxrxrx.coursescheduleremake.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.data.DataRepository

class DetailViewModel(private val repository: DataRepository, courseId: Int) : ViewModel() {

    val course: LiveData<Course> = repository.getCourse(courseId)

    fun delete() {
        course.value?.let {
            repository.delete(it)
        }
    }
}