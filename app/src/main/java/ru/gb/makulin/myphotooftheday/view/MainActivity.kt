package ru.gb.makulin.myphotooftheday.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.databinding.ActivityMainBinding
import ru.gb.makulin.myphotooftheday.view.mars.MarsViewPagerFragment
import ru.gb.makulin.myphotooftheday.view.photo.PhotoFragment
import ru.gb.makulin.myphotooftheday.view.settings.SettingsFragment

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val sp by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme()
        setContentView(binding.root)
        if (savedInstanceState == null) {
            setFragment(MarsViewPagerFragment.newInstance())
        }
        initNavigation()
    }


    private fun initNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navPhotoOfTheDay -> {
                    setFragment(PhotoFragment.newInstance())
                    true
                }
                R.id.navSettings -> {
                    setFragment(SettingsFragment())
                    true
                }
                R.id.navMarsRoverPhotos -> {
                    setFragment(MarsViewPagerFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, fragment)
            .addToBackStack("")
            .commit()
    }

    private fun setTheme() {
        when (sp.getString("theme", "MainTheme")) {
            "MainTheme" -> setTheme(R.style.MainTheme)
            "AlternativeTheme" -> setTheme(R.style.AlternativeTheme)
            else -> setTheme(R.style.MainTheme)
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