package com.sxrxrx.coursescheduleremake.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.data.Course
import com.sxrxrx.coursescheduleremake.paging.CourseAdapter
import com.sxrxrx.coursescheduleremake.paging.CourseViewHolder
import com.sxrxrx.coursescheduleremake.ui.add.AddCourseActivity
import com.sxrxrx.coursescheduleremake.ui.detail.DetailActivity
import com.sxrxrx.coursescheduleremake.ui.setting.SettingsActivity
import com.sxrxrx.coursescheduleremake.util.SortType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    private val listViewModel: ListViewModel by viewModel()
    private lateinit var rvCourse: RecyclerView
    private val courseAdapter: CourseAdapter by lazy {
        CourseAdapter(::onCourseClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFabClick()
        setUpRecycler()
        initAction()
        updateList()
    }

    private fun setUpRecycler() {
        rvCourse = findViewById(R.id.rv_course)
        rvCourse.layoutManager = LinearLayoutManager(this)
        rvCourse.adapter = courseAdapter
    }

    private fun onCourseClick(course: Course) {
        startActivity(
            Intent(
                this,
                DetailActivity::class.java
            ).putExtra(DetailActivity.COURSE_ID, course.id)
        )
    }

    private fun initAction() {
        val callback = Callback()
        val itemTouchHelper = ItemTouchHelper(callback)

        itemTouchHelper.attachToRecyclerView(rvCourse)
    }

    private fun updateList() {
        listViewModel.courses.observe(this) {
            courseAdapter.submitList(it)
            findViewById<TextView>(R.id.tv_empty_list).visibility =
                if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setFabClick() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this,AddCourseActivity::class.java))
        }

    }

    private fun showSortMenu() {
        val view = findViewById<View>(R.id.action_sort) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.sort_course, menu)

            setOnMenuItemClickListener {
                listViewModel.sort(
                    when (it.itemId) {
                        R.id.sort_time -> SortType.TIME
                        R.id.sort_course_name -> SortType.COURSE_NAME
                        else -> SortType.LECTURER
                    }
                )
                true
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showSortMenu()
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class Callback : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val course = (viewHolder as CourseViewHolder).getCourse()
            listViewModel.delete(course)
        }
    }
}