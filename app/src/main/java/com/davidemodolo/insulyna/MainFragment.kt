package com.davidemodolo.insulyna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private val PREF_NAME = "data"
    private val FIRST_START = "start"

    private lateinit var btnReset: ImageView
    private lateinit var btnAddCarbo: TextView
    private lateinit var viewModel: AppViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        btnReset = view.findViewById(R.id.btnReset)
        btnAddCarbo = view.findViewById(R.id.btnAddCarbo)

        val btnSettings = view.findViewById<ImageView>(R.id.settingsIcon)
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        val btnSavedFood = view.findViewById<TextView>(R.id.btnSavedFood)
        btnSavedFood.setOnClickListener {
            findNavController().navigate(R.id.foodFragment)
        }


        val addCarboEdit = view.findViewById<EditText>(R.id.addCarboEdit)
        val carboOn100Edit = view.findViewById<EditText>(R.id.carboOn100Edit)
        //val eatenQuantityEdit = view.findViewById<EditText>(R.id.eatenQuantityEdit)


        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        val foodTMP = viewModel.getFoodToAdd()
        if(foodTMP!=null) {
            if(foodTMP.piece)
                addCarboEdit.setText(foodTMP.carbo.toString())
            else
                carboOn100Edit.setText(foodTMP.carbo.toString())
        }

        return view
    }



}