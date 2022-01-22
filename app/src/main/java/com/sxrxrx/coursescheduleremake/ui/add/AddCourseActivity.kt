package com.sxrxrx.coursescheduleremake.ui.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.ui.home.HomeActivity
import com.sxrxrx.coursescheduleremake.ui.home.HomeViewModel
import com.sxrxrx.coursescheduleremake.ui.home.HomeViewModelFactory
import com.sxrxrx.coursescheduleremake.util.Event
import com.sxrxrx.coursescheduleremake.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var edCourseName: TextInputEditText
    private lateinit var edLecturer: TextInputEditText
    private lateinit var edNote: TextInputEditText
    private lateinit var daySpinner: Spinner
    private lateinit var btnStartTime: ImageButton
    private lateinit var btnEndTime: ImageButton
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var viewModel: AddCourseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)
        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this,factory).get(AddCourseViewModel::class.java)


        edCourseName = findViewById(R.id.add_ed_course)
        edLecturer = findViewById(R.id.add_ed_lecturer)
        edNote = findViewById(R.id.add_ed_note)
        daySpinner = findViewById(R.id.spinner_day)
        btnStartTime = findViewById(R.id.btn_start_time)
        btnEndTime = findViewById(R.id.btn_end_time)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = edCourseName.text.toString()
                val day  = daySpinner.selectedItemPosition
                val startTime = tvStartTime.text.toString()
                val endTime = tvEndTime.text.toString()
                val lecturer = edLecturer.text.toString()
                val note = edNote.text.toString()
                viewModel.insertCourse(courseName,day,startTime,endTime,lecturer,note)
                viewModel.saved.observe(this,::savedState)
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
            "setStartTime" -> tvStartTime.text = dateFormat.format(calendar.time)
            "setEndTime" -> tvEndTime.text = dateFormat.format(calendar.time)
            else -> {
            }
        }

    }


}