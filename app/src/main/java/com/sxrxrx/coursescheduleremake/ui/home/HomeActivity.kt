package com.sxrxrx.coursescheduleremake.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.ui.add.AddCourseActivity
import com.sxrxrx.coursescheduleremake.ui.list.ListActivity
import com.sxrxrx.coursescheduleremake.ui.setting.SettingsActivity
import com.sxrxrx.coursescheduleremake.util.DayName
import com.sxrxrx.coursescheduleremake.util.QueryType
import com.sxrxrx.coursescheduleremake.util.timeDifference
class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private var queryType = QueryType.CURRENT_DAY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = resources.getString(R.string.today_schedule)

        val factory = HomeViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        viewModel.getTodaySchedule().observe(this, Observer(this::showTodaySchedule))

    }

    private fun showTodaySchedule(course: Course?) {
        checkQueryType(course)
        course?.apply {
            val dayName = DayName.getByNumber(day)
            val time = String.format(getString(R.string.time_format), dayName, startTime, endTime)
            val remainingTime = timeDifference(day, startTime)

            val cardHome = findViewById<CardHomeView>(R.id.view_home)
            cardHome.setCourseName(courseName)
            cardHome.setLecturer(lecturer)
            cardHome.setNote(note)
            cardHome.setTime(time)
            cardHome.setRemainingTime(remainingTime)

        }

        findViewById<TextView>(R.id.tv_empty_home).visibility =
            if (course == null) View.VISIBLE else View.GONE
    }

    private fun checkQueryType(course: Course?) {
        if (course == null) {
            val newQueryType: QueryType = when (queryType) {
                QueryType.CURRENT_DAY -> QueryType.NEXT_DAY
                QueryType.NEXT_DAY -> QueryType.PAST_DAY
                else -> QueryType.CURRENT_DAY
            }
            viewModel.setQueryType(newQueryType)
            queryType = newQueryType
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.action_add -> Intent(this, AddCourseActivity::class.java)
            R.id.action_settings -> Intent(this, SettingsActivity::class.java)
            R.id.action_list -> Intent(this,ListActivity::class.java)
            else -> null
        } ?: return super.onOptionsItemSelected(item)

        startActivity(intent)
        return true
    }
}