package com.sxrxrx.coursescheduleremake.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.databinding.ActivityHomeBinding
import com.sxrxrx.coursescheduleremake.ui.add.AddCourseActivity
import com.sxrxrx.coursescheduleremake.ui.list.ListActivity
import com.sxrxrx.coursescheduleremake.ui.setting.SettingsActivity
import com.sxrxrx.coursescheduleremake.util.DayName
import com.sxrxrx.coursescheduleremake.util.QueryType
import com.sxrxrx.coursescheduleremake.util.timeDifference

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var queryType = QueryType.CURRENT_DAY

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.today_schedule)

        homeViewModel.getTodaySchedule().observe(this, Observer(this::showTodaySchedule))

    }

    private fun showTodaySchedule(course: Course?) {
        checkQueryType(course)
        course?.apply {
            val dayName = DayName.getByNumber(day)
            val time = String.format(getString(R.string.time_format), dayName, startTime, endTime)
            val remainingTime = timeDifference(day, startTime)

            binding.viewHome.setCourseName(courseName)
            binding.viewHome.setLecturer(lecturer)
            binding.viewHome.setNote(note)
            binding.viewHome.setTime(time)
            binding.viewHome.setRemainingTime(remainingTime)

        }

        binding.tvEmptyHome.visibility =
            if (course == null) View.VISIBLE else View.GONE
    }

    private fun checkQueryType(course: Course?) {
        if (course == null) {
            val newQueryType: QueryType = when (queryType) {
                QueryType.CURRENT_DAY -> QueryType.NEXT_DAY
                QueryType.NEXT_DAY -> QueryType.PAST_DAY
                else -> QueryType.CURRENT_DAY
            }
            homeViewModel.setQueryType(newQueryType)
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