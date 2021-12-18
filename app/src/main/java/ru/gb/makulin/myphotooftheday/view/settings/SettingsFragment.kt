package ru.gb.makulin.myphotooftheday.view.settings

import android.content.res.Configuration
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.gb.makulin.myphotooftheday.R

class SettingsFragment : PreferenceFragmentCompat(),Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        requireActivity().recreate()
       return true
    }


}