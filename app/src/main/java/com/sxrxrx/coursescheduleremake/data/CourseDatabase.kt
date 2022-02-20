package com.sxrxrx.coursescheduleremake.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao

}
