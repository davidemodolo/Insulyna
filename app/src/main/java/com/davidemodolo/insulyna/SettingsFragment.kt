package com.davidemodolo.insulyna

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class SettingsFragment : Fragment() {
    private val PREF_NAME = "data"
    private val THEME = "theme"

    private lateinit var themeDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        val btnTheme = view.findViewById<TextView>(R.id.btnTheme)
        btnTheme.setOnClickListener {
            selectThemeColor()
        }

        return view
    }

    private fun selectThemeColor()
    {
        themeDialog = Dialog(requireContext())
        themeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        themeDialog.setCancelable(true)
        themeDialog.setContentView(R.layout.dialog_theme)
        themeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val col1 = themeDialog.findViewById<ImageView>(R.id.color1)
        col1.setOnClickListener {
            setThemeColor("green")
        }
        val col2 = themeDialog.findViewById<ImageView>(R.id.color2)
        col2.setOnClickListener {
            setThemeColor("pink")
        }
        val col3 = themeDialog.findViewById<ImageView>(R.id.color3)
        col3.setOnClickListener {
            setThemeColor("purple")
        }
        val col4 = themeDialog.findViewById<ImageView>(R.id.color4)
        col4.setOnClickListener {
            setThemeColor("red")
        }
        val col5 = themeDialog.findViewById<ImageView>(R.id.color5)
        col5.setOnClickListener {
            setThemeColor("blue")
        }
        val col6 = themeDialog.findViewById<ImageView>(R.id.color6)
        col6.setOnClickListener {
            setThemeColor("yellow")
        }
        themeDialog.show()
    }
    private fun setThemeColor(chosenColor: String)
    {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)
        val editor = sharedPref?.edit()
        editor?.putString(THEME, chosenColor)
        editor?.apply()
        themeDialog.dismiss()
        Toast.makeText(
            requireContext(),
            "Riavvia l'app per apportare la modifica",
            Toast.LENGTH_SHORT
        ).show()

    }

}