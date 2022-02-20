package com.sxrxrx.coursescheduleremake

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.sxrxrx.coursescheduleremake.di.dailyReminderModule
import com.sxrxrx.coursescheduleremake.di.databaseModule
import com.sxrxrx.coursescheduleremake.di.repositoryModule
import com.sxrxrx.coursescheduleremake.di.viewModelModule
import com.sxrxrx.coursescheduleremake.util.NightMode
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

class MainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_auto)
        )?.apply {
            val mode = NightMode.valueOf(this.toUpperCase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainActivity)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    viewModelModule,
                    dailyReminderModule
                )
            )


        }
    }
}