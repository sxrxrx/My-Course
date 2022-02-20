package com.sxrxrx.coursescheduleremake.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.databinding.ActivityDetailBinding

import com.sxrxrx.coursescheduleremake.util.DayName.Companion.getByNumber

class DetailActivity : AppCompatActivity() {

    companion object {
        const val COURSE_ID = "courseId"
    }

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseId = intent.getIntExtra(COURSE_ID, 0)
//        val factory = DetailViewModelFactory.createFactory(this, courseId)
//        viewModel = ViewModelProvider(this,factory).get(DetailViewModel::class.java)

        detailViewModel.setCourseId(courseId)
        detailViewModel.course.observe(this, Observer(this::showCourseDetail))

    }

    private fun showCourseDetail(course: Course?) {
        course?.apply {
            val timeString = getString(R.string.time_format)
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            binding.tvCourseName.text = courseName
            binding.tvTime.text = timeFormat
            binding.tvLecturer.text = lecturer
            binding.tvNote.text = note
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.delete_alert))
                    setNegativeButton(getString(R.string.no), null)
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        detailViewModel.delete()
                        finish()
                    }
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}