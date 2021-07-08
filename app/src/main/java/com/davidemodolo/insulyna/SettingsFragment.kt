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
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

class SettingsFragment : Fragment() {
    private val PREF_NAME = "data"
    private val THEME = "theme"
    private val DAILY = "daily"
    private val GOAL = "goal"
    private val RATEO = "rateo"
    private val SENS = "sens"

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
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)
        var daily = sharedPref?.getFloat(DAILY, 0.0F)
        var goal = sharedPref?.getFloat(GOAL, 0.0F)
        var rateo = sharedPref?.getFloat(RATEO, 0.0F)
        var sens = sharedPref?.getFloat(SENS, 0.0F)

        val dailyLayout = view.findViewById<ConstraintLayout>(R.id.daily)
        val dailyName = dailyLayout.findViewById<TextView>(R.id.preferenceName)
        dailyName.text = getString(R.string.dailyUI_label)
        val dailyValue = dailyLayout.findViewById<EditText>(R.id.preferenceValue)
        if(daily != 0.0F)
            dailyValue.setText(daily.toString())
        val dailyQuestion = dailyLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        dailyQuestion.setOnClickListener {
            //funzione che apre un dialog globale della chat, passando un parametro per le cose da vedere
            //bottone globale del dialog che salva il valore
        }

        val goalLayout = view.findViewById<ConstraintLayout>(R.id.goal)
        val goalName = goalLayout.findViewById<TextView>(R.id.preferenceName)
        goalName.text = getString(R.string.goal_label)
        val goalValue = goalLayout.findViewById<EditText>(R.id.preferenceValue)
        if(goal != 0.0F)
            goalValue.setText(goal.toString())
        //val goalQuestion = goalLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val rateoLayout = view.findViewById<ConstraintLayout>(R.id.rateo)
        val rateoName = rateoLayout.findViewById<TextView>(R.id.preferenceName)
        rateoName.text = getString(R.string.rateo_label)
        val rateoValue = rateoLayout.findViewById<EditText>(R.id.preferenceValue)
        if(rateo != 0.0F)
            rateoValue.setText(rateo.toString())
        //val rateoQuestion = rateoLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val sensLayout = view.findViewById<ConstraintLayout>(R.id.sensitivity)
        val sensName = sensLayout.findViewById<TextView>(R.id.preferenceName)
        sensName.text = getString(R.string.sensibility_label)
        val sensValue = sensLayout.findViewById<EditText>(R.id.preferenceValue)
        if(sens != 0.0F)
            sensValue.setText(sens.toString())
        //val sensQuestion = sensLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val btnSave = view.findViewById<TextView>(R.id.btnSavePreference)
        btnSave.setOnClickListener {
            daily = stringToFloat(dailyValue.text.toString())
            goal =stringToFloat(goalValue.text.toString())
            rateo = stringToFloat(rateoValue.text.toString())
            sens = stringToFloat(sensValue.text.toString())

            val editor = sharedPref?.edit()

            editor?.putFloat(DAILY, daily!!)
            editor?.putFloat(GOAL, goal!!)
            editor?.putFloat(RATEO, rateo!!)
            editor?.putFloat(SENS, sens!!)

            editor?.apply()
            Toast.makeText(
                requireContext(),
                "Dati salvati",
                Toast.LENGTH_SHORT
            ).show()

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
        (activity as MainActivity?)?.switchTheme()
        findNavController().navigate(R.id.settingsFragment)
        themeDialog.dismiss()

    }

    private fun stringToFloat(string: String): Float {
        if (string =="") return 0.0F
        var stringTMP = ""
        string.forEach {
            stringTMP += if (it == ',')
                '.'
            else
                it
        }
        return stringTMP.toFloat()
    }

}