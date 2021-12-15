package ru.gb.makulin.myphotooftheday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.makulin.myphotooftheday.R
import ru.gb.makulin.myphotooftheday.view.photo.PhotoFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, PhotoFragment.newInstance())
                .commit()
        }
    }
}