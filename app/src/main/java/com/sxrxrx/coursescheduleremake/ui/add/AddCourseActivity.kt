package com.sxrxrx.coursescheduleremake.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.ui.home.HomeActivity
import com.sxrxrx.coursescheduleremake.util.Event
import com.sxrxrx.coursescheduleremake.util.TimePickerFragment
import com.sxrxrx.coursescheduleremake.databinding.ActivityAddCourseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private val addCourseviewModel: AddCourseViewModel by viewModel()
    private lateinit var binding: ActivityAddCourseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.add_course)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = binding.addEdCourse.text.toString()
                val day  = binding.spinnerDay.selectedItemPosition
                val startTime = binding.tvStartTime.text.toString()
                val endTime = binding.tvEndTime.text.toString()
                val lecturer = binding.addEdLecturer.text.toString()
                val note = binding.addEdNote.text.toString()
                addCourseviewModel.insertCourse(courseName,day,startTime,endTime,lecturer,note)
                addCourseviewModel.saved.observe(this,::savedState)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savedState(event: Event<Boolean>?) {
        val isSaved = event?.getContentIfNotHandled()
        if (isSaved != true){
            return
        }else{
            startActivity(Intent(this,HomeActivity::class.java)).also {
                finish()
            }
        }

    }

    fun setStartTime(view: View) {
        val timeFragment = TimePickerFragment()
        timeFragment.show(supportFragmentManager, "setStartTime")
    }

    fun setEndTime(view : View){
        val timeFragment = TimePickerFragment()
        timeFragment.show(supportFragmentManager, "setEndTime")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            "setStartTime" -> binding.tvStartTime.text = dateFormat.format(calendar.time)
            "setEndTime" -> binding.tvEndTime.text = dateFormat.format(calendar.time)
            else -> {
            }
        }

    }
}