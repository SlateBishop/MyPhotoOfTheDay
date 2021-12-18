package ru.gb.makulin.myphotooftheday.view.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.gb.makulin.myphotooftheday.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}