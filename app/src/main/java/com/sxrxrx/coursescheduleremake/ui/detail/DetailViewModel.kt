package com.sxrxrx.coursescheduleremake.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.data.DataRepository

class DetailViewModel(val repository: DataRepository) : ViewModel() {

    private val _courseId = MutableLiveData<Int>()
    private val _course = _courseId.switchMap {
        repository.getCourse(it)
    }

    val course: LiveData<Course> = _course

    fun setCourseId(courseId: Int?) {
        if(courseId == _courseId.value) {
            return
        }
        _courseId.value = courseId
    }


    fun delete() {
        course.value?.let {
            repository.delete(it)
        }
    }
}