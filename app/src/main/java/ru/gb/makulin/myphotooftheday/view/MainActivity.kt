package ru.gb.makulin.myphotooftheday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.view.photo.PhotoFragment

class MainActivity : AppCompatActivity(), Preference.OnPreferenceChangeListener {

    private val sp by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (sp.getString("theme", "")) {
            "MainTheme" -> setTheme(R.style.MainTheme)
            "AlternativeTheme" -> setTheme(R.style.AlternativeTheme)
        }
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, PhotoFragment.newInstance())
                .commit()
        }
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        recreate()
        return true
    }
}