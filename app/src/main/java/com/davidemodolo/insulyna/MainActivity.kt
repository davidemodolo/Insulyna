package com.davidemodolo.insulyna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidemodolo.insulyna.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, MainFragment.newInstance())
                .commitNow()
        }
    }
}