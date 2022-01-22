package com.sxrxrx.coursescheduleremake.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.sxrxrx.coursescheduleremake.R
import com.sxrxrx.coursescheduleremake.notification.DailyReminder
import com.sxrxrx.coursescheduleremake.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val listPreference =
            findPreference<ListPreference>(getString(R.string.pref_key_dark))
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            val value = listPreference.findIndexOfValue(newValue.toString())
            updateTheme(value)
        }

        val dailyReminder = DailyReminder()
        val prefNotification =
            findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { _, _ ->
            if(!prefNotification.isChecked){
                dailyReminder.setDailyReminder(requireContext())
            }else{
                dailyReminder.cancelAlarm(requireContext())
            }

            true

            }

    }

    private fun updateTheme(nightMode: Int): Boolean {
        when(nightMode){
            0 ->  AppCompatDelegate.setDefaultNightMode(NightMode.AUTO.value)
            1 ->  AppCompatDelegate.setDefaultNightMode(NightMode.ON.value)
            2 ->  AppCompatDelegate.setDefaultNightMode(NightMode.OFF.value)
        }
        requireActivity().recreate()
        return true
    }
}