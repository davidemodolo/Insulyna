package com.davidemodolo.insulyna

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    private val PREF_NAME = "data"
    private val FIRST_START = "start"
    private val GOAL = "goal"
    private var goal = 0.0F
    private val RATEO = "rateo"
    private var rateo = 0.0F
    private val SENS = "sens"
    private var sens = 0.0F

    private lateinit var carboTot: EditText
    private lateinit var glycemiaEdit: EditText
    private lateinit var addCarboEdit: EditText
    private lateinit var carboOn100Edit: EditText
    private lateinit var eatenQuantityEdit: EditText

    private lateinit var uiValue: TextView


    private lateinit var viewModel: AppViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)

        if (sharedPref?.getBoolean(FIRST_START, true) == true) {
            //navigate to disclaimer fragment if it's the first start
            findNavController().navigate(R.id.disclaimerFragment)
        }

        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)


        uiValue = view.findViewById(R.id.units_value)
        goal = sharedPref?.getFloat(GOAL, 0.0F)!!
        rateo = sharedPref.getFloat(RATEO, 0.0F)
        sens = sharedPref.getFloat(SENS, 0.0F)


        val btnSettings = view.findViewById<ImageView>(R.id.settingsIcon)
        btnSettings.setOnClickListener {
            btnSettings.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.alpha))
            findNavController().navigate(R.id.settingsFragment)
        }

        val btnSavedFood = view.findViewById<TextView>(R.id.btnSavedFood)
        btnSavedFood.setOnClickListener {
            btnSavedFood.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.alpha
                )
            )
            findNavController().navigate(R.id.foodFragment)
        }

        addCarboEdit = view.findViewById(R.id.addCarboEdit)
        carboOn100Edit = view.findViewById(R.id.carboOn100Edit)
        carboOn100Edit.addTextChangedListener { calculateCarboToAdd() }

        eatenQuantityEdit = view.findViewById(R.id.eatenQuantityEdit)
        eatenQuantityEdit.addTextChangedListener { calculateCarboToAdd() }

        carboTot = view.findViewById(R.id.editCarboTot)
        if (viewModel.getCarbo() > 0) {
            carboTot.setText(String.format("%.2f", viewModel.getCarbo()))
        }
        carboTot.addTextChangedListener { calculateUI() }
        glycemiaEdit = view.findViewById(R.id.glycemiaEdit)
        glycemiaEdit.addTextChangedListener { calculateUI() }

        val btnAddCarbo = view.findViewById<TextView>(R.id.btnAddCarbo)
        btnAddCarbo.setOnClickListener {
            btnAddCarbo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.alpha))
            val totalTMP = if (carboTot.text.toString() == "") 0.0F
            else {
                stringToFloat(carboTot.text.toString())
            }
            val value = totalTMP + stringToFloat(addCarboEdit.text.toString())
            if (value != 0.0F) {
                carboTot.setText(String.format("%.2f", value))
                viewModel.setCarbo(value)
            }
            carboOn100Edit.setText("")
            carboOn100Edit.clearFocus()
            eatenQuantityEdit.setText("")
            eatenQuantityEdit.clearFocus()
            addCarboEdit.setText("")
            addCarboEdit.clearFocus()
            viewModel.resetFoodToAdd()
        }

        val btnReset = view.findViewById<ImageView>(R.id.btnReset)
        btnReset.setOnClickListener {
            btnReset.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.rotate))
            viewModel.resetFoodToAdd()
            viewModel.setCarbo(0.0F)
            addCarboEdit.setText("")
            addCarboEdit.clearFocus()
            carboOn100Edit.setText("")
            carboOn100Edit.clearFocus()
            eatenQuantityEdit.setText("")
            eatenQuantityEdit.clearFocus()
            carboTot.setText("")
            carboTot.clearFocus()
            glycemiaEdit.setText("")
            glycemiaEdit.clearFocus()
        }

        val foodTMP = viewModel.getFoodToAdd()
        if (foodTMP.first > 0) {
            if (foodTMP.second == 0.0F) {
                carboOn100Edit.setText("")
                eatenQuantityEdit.setText("")
                addCarboEdit.setText(foodTMP.first.toString())
            } else {
                addCarboEdit.setText("")
                carboOn100Edit.setText(foodTMP.first.toString())
                eatenQuantityEdit.setText(foodTMP.second.toString())
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(PREF_NAME, 0)

        if (sharedPref?.getBoolean(FIRST_START, true) == false) {
            if (goal == 0.0F || rateo == 0.0F || sens == 0.0F) {
                Snackbar.make(
                    view,
                    "Imposta i tuoi valori nelle impostazioni per iniziare",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
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

    private fun calculateUI() { //function to calculate UI
        if (sens != 0.0F) {
            val carboTOT = stringToFloat(carboTot.text.toString())
            viewModel.setCarbo(carboTOT)
            val glycemiaTOT = stringToFloat(glycemiaEdit.text.toString())
            val result = carboTOT / rateo + (glycemiaTOT - goal) / sens
            if (result > 0)
                uiValue.text = String.format("%.2f", result)
            if (glycemiaTOT == 0.0F)
                uiValue.text = getString(R.string.placeholder_UI)
        }
    }

    private fun calculateCarboToAdd() { //function to calculate the proportion from the table
        val carbo = stringToFloat(carboOn100Edit.text.toString())
        val eaten = stringToFloat(eatenQuantityEdit.text.toString())
        val result = carbo * eaten / 100
        if (result > 0)
            addCarboEdit.setText(String.format("%.2f", result))
    }


}