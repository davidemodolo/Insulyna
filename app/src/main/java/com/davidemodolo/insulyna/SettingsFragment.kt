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
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text

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
        var daily = sharedPref?.getString(DAILY, "")
        var goal = sharedPref?.getString(GOAL, "")
        var rateo = sharedPref?.getString(RATEO, "")
        var sens = sharedPref?.getString(SENS, "")

        val dailyLayout = view.findViewById<LinearLayout>(R.id.daily)
        val dailyName = dailyLayout.findViewById<TextView>(R.id.preferenceName)
        dailyName.text = getString(R.string.dailyUI_label)
        val dailyValue = dailyLayout.findViewById<EditText>(R.id.preferenceValue)
        dailyValue.setText(daily)
        val dailyQuestion = dailyLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        dailyQuestion.setOnClickListener {
            //funzione che apre un dialog globale della chat, passando un parametro per le cose da vedere
            //bottone globale del dialog che salva il valore
        }

        val goalLayout = view.findViewById<LinearLayout>(R.id.goal)
        val goalName = goalLayout.findViewById<TextView>(R.id.preferenceName)
        goalName.text = getString(R.string.goal_label)
        val goalValue = goalLayout.findViewById<EditText>(R.id.preferenceValue)
        goalValue.setText(goal)
        //val goalQuestion = goalLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val rateoLayout = view.findViewById<LinearLayout>(R.id.rateo)
        val rateoName = rateoLayout.findViewById<TextView>(R.id.preferenceName)
        rateoName.text = getString(R.string.rateo_label)
        val rateoValue = rateoLayout.findViewById<EditText>(R.id.preferenceValue)
        rateoValue.setText(rateo)
        //val rateoQuestion = rateoLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val sensLayout = view.findViewById<LinearLayout>(R.id.sensitivity)
        val sensName = sensLayout.findViewById<TextView>(R.id.preferenceName)
        sensName.text = getString(R.string.sensibility_label)
        val sensValue = sensLayout.findViewById<EditText>(R.id.preferenceValue)
        sensValue.setText(sens)
        //val sensQuestion = sensLayout.findViewById<ImageView>(R.id.preferenceQuestion)

        val btnSave = view.findViewById<TextView>(R.id.btnSavePreference)
        btnSave.setOnClickListener {
            daily = dailyValue.text.toString()
            goal = goalValue.text.toString()
            rateo = rateoValue.text.toString()
            sens = sensValue.text.toString()

            val editor = sharedPref?.edit()

            editor?.putString(DAILY, daily)
            editor?.putString(GOAL, goal)
            editor?.putString(RATEO, rateo)
            editor?.putString(SENS, sens)

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

}