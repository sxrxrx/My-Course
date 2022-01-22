package com.sxrxrx.coursescheduleremake.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.data.DataRepository
import com.sxrxrx.coursescheduleremake.util.QueryType

class HomeViewModel(private val repository: DataRepository): ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    fun getTodaySchedule(): LiveData<Course?> =
        repository.getNearestSchedule(QueryType.CURRENT_DAY)
}
