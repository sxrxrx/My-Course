package com.sxrxrx.coursescheduleremake.di

import androidx.room.Room
import com.sxrxrx.coursescheduleremake.data.CourseDatabase
import com.sxrxrx.coursescheduleremake.data.DataRepository
import com.sxrxrx.coursescheduleremake.notif.DailyReminder
import com.sxrxrx.coursescheduleremake.ui.add.AddCourseViewModel
import com.sxrxrx.coursescheduleremake.ui.detail.DetailViewModel
import com.sxrxrx.coursescheduleremake.ui.home.HomeViewModel
import com.sxrxrx.coursescheduleremake.ui.list.ListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    factory { get<CourseDatabase>().courseDao() }
    single {
        Room.databaseBuilder(androidContext(), CourseDatabase::class.java, "courses.db").build()
    }
}

val repositoryModule = module {
    single { DataRepository(get()) }
}

val dailyReminderModule = module {
    single { DailyReminder(get()) }
}

val viewModelModule = module {
    viewModel { AddCourseViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { ListViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}