package com.davidemodolo.insulyna

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "data"
    private val THEME = "theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        switchTheme()
    }

    fun switchTheme()
    {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, 0)

        when (sharedPref.getString(THEME, "pink")) {
            "green" -> this.setTheme(R.style.ThemeGreen)
            "pink" -> this.setTheme(R.style.ThemePink)
            "yellow" -> this.setTheme(R.style.ThemeYellow)
            "blue" -> this.setTheme(R.style.ThemeBlue)
            "red" -> this.setTheme(R.style.ThemeRed)
            "purple" -> this.setTheme(R.style.ThemePurple)
            else -> { // Note the block
                Toast.makeText(
                    this,
                    "Errore nel caricamento del tema personale",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}