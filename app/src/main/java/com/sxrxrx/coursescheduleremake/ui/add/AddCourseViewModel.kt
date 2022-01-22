package com.sxrxrx.coursescheduleremake.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.data.DataRepository
import com.sxrxrx.coursescheduleremake.util.Event

class AddCourseViewModel(private val repository: DataRepository) : ViewModel() {

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>>
        get() = _saved

    fun insertCourse(
        courseName: String,
        day: Int,
        startTime: String,
        endTime: String,
        lecturer: String,
        note: String
    ) {
        if (courseName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            _saved.value = Event(false)
            return
        }

        val course = Course(
            courseName = courseName,
            day = day + 1,
            startTime = startTime,
            endTime = endTime,
            lecturer = lecturer,
            note = note
        )
        repository.insert(course)
        _saved.value = Event(true)
    }
}