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
import org.w3c.dom.Text

class SettingsFragment : Fragment() {
    private val PREF_NAME = "data"
    private val THEME = "theme"
    private val DAILY = "daily"
    private val GOAL = "goal"
    private val RATEO = "rateo"
    private val SENS = "sens"

    private lateinit var themeDialog: Dialog
    private lateinit var questionDialog: Dialog
    private lateinit var dailyValue: EditText
    private lateinit var rateoValue: EditText
    private lateinit var sensValue: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.wait_anim, R.anim.slide_right)
                .replace(R.id.nav_host_fragment, MainFragment()).commit()
            //findNavController().navigate(R.id.mainFragment)
        }
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)
        var daily = sharedPref?.getFloat(DAILY, 0.0F)
        var goal = sharedPref?.getFloat(GOAL, 0.0F)
        var rateo = sharedPref?.getFloat(RATEO, 0.0F)
        var sens = sharedPref?.getFloat(SENS, 0.0F)

        val dailyLayout = view.findViewById<ConstraintLayout>(R.id.daily)
        val dailyName = dailyLayout.findViewById<TextView>(R.id.preferenceName)
        dailyName.text = getString(R.string.dailyUI_label)
        dailyValue = dailyLayout.findViewById(R.id.preferenceValue)
        if (daily != 0.0F)
            dailyValue.setText(daily.toString())
        val dailyQuestion = dailyLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        dailyQuestion.setOnClickListener {
            questionDialogFun(
                getString(R.string.dailyUI_extended),
                getString(R.string.dailyUI_description)
            )
        }

        val goalLayout = view.findViewById<ConstraintLayout>(R.id.goal)
        val goalName = goalLayout.findViewById<TextView>(R.id.preferenceName)
        goalName.text = getString(R.string.goal_label)
        val goalValue = goalLayout.findViewById<EditText>(R.id.preferenceValue)
        if (goal != 0.0F)
            goalValue.setText(goal.toString())
        val goalQuestion = goalLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        goalQuestion.setOnClickListener {
            questionDialogFun(
                getString(R.string.goal_extended),
                getString(R.string.goal_description)
            )
        }

        val rateoLayout = view.findViewById<ConstraintLayout>(R.id.rateo)
        val rateoName = rateoLayout.findViewById<TextView>(R.id.preferenceName)
        rateoName.text = getString(R.string.rateo_label)
        rateoValue = rateoLayout.findViewById(R.id.preferenceValue)
        if (rateo != 0.0F)
            rateoValue.setText(rateo.toString())
        val rateoQuestion = rateoLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        rateoQuestion.setOnClickListener {
            questionDialogFun(
                getString(R.string.rateo_extended),
                getString(R.string.rateo_description)
            )
        }

        val sensLayout = view.findViewById<ConstraintLayout>(R.id.sensitivity)
        val sensName = sensLayout.findViewById<TextView>(R.id.preferenceName)
        sensName.text = getString(R.string.sensibility_label)
        sensValue = sensLayout.findViewById(R.id.preferenceValue)
        if (sens != 0.0F)
            sensValue.setText(sens.toString())
        val sensQuestion = sensLayout.findViewById<ImageView>(R.id.preferenceQuestion)
        sensQuestion.setOnClickListener {
            questionDialogFun(
                getString(R.string.sensibility_extended),
                getString(R.string.sensibility_description)
            )
        }

        val btnSave = view.findViewById<TextView>(R.id.btnSavePreference)
        btnSave.setOnClickListener {
            daily = stringToFloat(dailyValue.text.toString())
            goal = stringToFloat(goalValue.text.toString())
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

        val btnBackup = view.findViewById<TextView>(R.id.btnBackup)
        btnBackup.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_backup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        val btnTheme = view.findViewById<TextView>(R.id.btnTheme)
        btnTheme.setOnClickListener {
            selectThemeColor()
        }


        return view
    }

    private fun selectThemeColor() {
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

    private fun setThemeColor(chosenColor: String) {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)
        val editor = sharedPref?.edit()
        editor?.putString(THEME, chosenColor)
        editor?.apply()
        (activity as MainActivity?)?.switchTheme()
        findNavController().navigate(R.id.settingsFragment)
        themeDialog.dismiss()

    }

    private fun stringToFloat(string: String): Float {
        if (string == "") return 0.0F
        var stringTMP = ""
        string.forEach {
            stringTMP += if (it == ',')
                '.'
            else
                it
        }
        return stringTMP.toFloat()
    }

    private fun questionDialogFun(title: String, desc: String) {
        questionDialog = Dialog(requireContext())
        questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        questionDialog.setCancelable(true)
        questionDialog.setContentView(R.layout.dialog_question)
        questionDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogTitle = questionDialog.findViewById<TextView>(R.id.dialogTitle)
        dialogTitle.text = title
        val dialogDesc = questionDialog.findViewById<TextView>(R.id.dialogText)
        dialogDesc.text = desc
        val dialogBtn = questionDialog.findViewById<TextView>(R.id.btnCalculate)
        when (title) {
            getString(R.string.rateo_extended) -> {
                dialogBtn.setOnClickListener {
                    val value = stringToFloat(dailyValue.text.toString())
                    if (value != 0.0F) {
                        val result = 500.0F / value
                        rateoValue.setText(String.format("%.2f", result))
                        val sharedPref: SharedPreferences? =
                            activity?.getSharedPreferences(PREF_NAME, 0)
                        val editor = sharedPref?.edit()
                        editor?.putFloat(RATEO, result)
                        editor?.apply()
                        questionDialog.dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Manca il fabb. giornaliero",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            getString(R.string.sensibility_extended) -> {
                dialogBtn.setOnClickListener {
                    val value = stringToFloat(dailyValue.text.toString())
                    if (value != 0.0F) {
                        val result = 1800.0F / stringToFloat(dailyValue.text.toString())
                        sensValue.setText(String.format("%.2f", result))
                        val sharedPref: SharedPreferences? =
                            activity?.getSharedPreferences(PREF_NAME, 0)
                        val editor = sharedPref?.edit()
                        editor?.putFloat(SENS, result)
                        editor?.apply()
                        questionDialog.dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Manca il fabb. giornaliero",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else -> {
                dialogBtn.visibility = View.GONE
            }
        }
        questionDialog.show()
    }

}