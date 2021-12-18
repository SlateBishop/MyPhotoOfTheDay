package ru.gb.makulin.myphotooftheday.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.view.photo.PhotoFragment

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val sp by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, PhotoFragment.newInstance())
                .commit()
        }
    }

    private fun setTheme() {
        when (sp.getString("theme", "")) {
            "MainTheme" -> setTheme(R.style.MainTheme)
            "AlternativeTheme" -> setTheme(R.style.AlternativeTheme)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        recreate()
    }

    override fun onResume() {
        super.onResume()
        sp.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sp.unregisterOnSharedPreferenceChangeListener(this)
    }
}