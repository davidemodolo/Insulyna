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
import com.davidemodolo.insulyna.R
import com.davidemodolo.insulyna.SettingsFragment


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private val PREF_NAME = "data"
    private val FIRST_START = "start"
    private val COLOR_MAIN = "mainc"
    private val COLOR_SECOND = "secoc"

    private lateinit var topBar: ConstraintLayout
    private lateinit var syringeImg: ImageView
    private lateinit var btnSavedFood: TextView
    private lateinit var titleMeal: TextView
    private lateinit var btnReset: ImageView
    private lateinit var titleAddCarbo: TextView
    private lateinit var line1: View
    private lateinit var proportionLabel: TextView
    private lateinit var line2: View
    private lateinit var btnAddCarbo: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        topBar = view.findViewById(R.id.topBar)
        syringeImg = view.findViewById(R.id.syringeImg)
        btnSavedFood = view.findViewById(R.id.btnSavedFood)
        titleMeal = view.findViewById(R.id.titleMeal)
        btnReset = view.findViewById(R.id.btnReset)
        titleAddCarbo = view.findViewById(R.id.titleAddCarbo)
        line1 = view.findViewById(R.id.line)
        proportionLabel = view.findViewById(R.id.proportion)
        line2 = view.findViewById(R.id.line2)
        btnAddCarbo = view.findViewById(R.id.btnAddCarbo)

        val btnSettings = view.findViewById<ImageView>(R.id.settingsIcon)
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        updateUI()
        return view
    }

    private fun updateUI(){
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)
        val colorMain = sharedPref?.getString(COLOR_MAIN, "#35BF7E")
        val color1 = Color.parseColor(colorMain)
        val colorSecond = sharedPref?.getString(COLOR_SECOND, "#CBEEDA")
        val color2 = Color.parseColor(colorSecond)

        topBar.setBackgroundColor(color1)
        val unwrappedSyringe = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_syringe)
        val wrappedSyringe = DrawableCompat.wrap(unwrappedSyringe!!)
        DrawableCompat.setTint(wrappedSyringe, color2)
        syringeImg.setImageDrawable(wrappedSyringe)

        val unwrappedButton = AppCompatResources.getDrawable(requireContext(), R.drawable.background_button)
        val wrappedButton = DrawableCompat.wrap(unwrappedButton!!)
        DrawableCompat.setTint(wrappedButton, color1)

        val unwrappedReset = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_reset)
        val wrappedReset = DrawableCompat.wrap(unwrappedReset!!)
        DrawableCompat.setTint(wrappedReset, color1)
        btnReset.setImageDrawable(wrappedReset)

        btnSavedFood.background = wrappedButton
        btnAddCarbo.background = wrappedButton

        line1.setBackgroundColor(color1)
        line2.setBackgroundColor(color1)

        proportionLabel.setTextColor(color1)
        titleMeal.setTextColor(color1)

    }


}