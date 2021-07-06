package com.davidemodolo.insulyna.ui.main

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.davidemodolo.insulyna.MainActivity
import com.davidemodolo.insulyna.R
import com.davidemodolo.insulyna.SettingsFragment


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private val PREF_NAME = "data"
    private val FIRST_START = "start"

    private lateinit var btnSavedFood: TextView
    private lateinit var btnReset: ImageView
    private lateinit var btnAddCarbo: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        btnSavedFood = view.findViewById(R.id.btnSavedFood)
        btnReset = view.findViewById(R.id.btnReset)
        btnAddCarbo = view.findViewById(R.id.btnAddCarbo)

        val btnSettings = view.findViewById<ImageView>(R.id.settingsIcon)
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        return view
    }



}