package com.sxrxrx.coursescheduleremake.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sxrxrx.coursescheduleremake.util.*
import com.sxrxrx.coursescheduleremake.util.QueryUtil.nearestQuery
import java.util.*

class DataRepository(private val dao: CourseDao) {

    fun getNearestSchedule(queryType: QueryType) : LiveData<Course?> {
        val query = nearestQuery(queryType)

        return dao.getNearestSchedule(query)
     }

    fun getAllCourse(sortType: SortType): LiveData<PagedList<Course>> {
        val query = QueryUtil.sortedQuery(sortType)
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(dao.getAll(query),config).build()
    }

    fun getCourse(id: Int) : LiveData<Course> {
        return dao.getCourse(id)
    }

    fun getTodaySchedule() : List<Course> {
        val date = Calendar.getInstance()
        val today = date.get(Calendar.DAY_OF_WEEK)
        return dao.getTodaySchedule(today)
    }

    fun insert(course: Course) = executeThread {
        dao.insert(course)
    }

    fun delete(course: Course) = executeThread {
        dao.delete(course)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}