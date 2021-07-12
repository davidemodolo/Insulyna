package com.davidemodolo.insulyna.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.davidemodolo.insulyna.AppViewModel
import com.davidemodolo.insulyna.R
import com.davidemodolo.insulyna.food.db.Food
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class FoodFragment : Fragment(), FoodAdapter.FoodListener {

    private lateinit var viewModel: AppViewModel
    private val MAIN = 1
    private val EDIT = 2
    private val DELETE = 3

    private var foods = ArrayList<Food>()
    private lateinit var foodRecycler: RecyclerView
    private lateinit var containerForFunction: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        containerForFunction = container!!
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        foodRecycler = view.findViewById(R.id.recyclerFood)

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheet =
                layoutInflater.inflate(R.layout.dialog_bottom_addfood, container, false)
            dialog.setContentView(bottomSheet)
            val nameText = dialog.findViewById<TextInputEditText>(R.id.nameText)
            val carboText = dialog.findViewById<TextInputEditText>(R.id.carboText)
            val btn100 = dialog.findViewById<TextView>(R.id.switchA)
            val btnPiece = dialog.findViewById<TextView>(R.id.switchB)

            btn100?.setOnClickListener {
                val nameTmp = nameText?.text.toString()
                val carboTmp = carboText?.text.toString()
                if (nameTmp != "" && carboTmp != "") {
                    viewModel.insertFood(Food(0, nameTmp, carboTmp.toInt(), false))
                    dialog.dismiss()
                }
            }
            btnPiece?.setOnClickListener {
                val nameTmp = nameText?.text.toString()
                val carboTmp = carboText?.text.toString()
                if (nameTmp != "" && carboTmp != "") {
                    viewModel.insertFood(Food(0, nameTmp, carboTmp.toInt(), true))
                    dialog.dismiss()
                }
            }
            dialog.show()
        }

        val search = view.findViewById<TextInputEditText>(R.id.searchText)
        search.addTextChangedListener {
            val searchedText = search.text.toString()
            val searchedList = ArrayList<Food>()
            var maxSF = 0.0
            foods.forEach {//scorro la lista la prima volta per trovare la massima similarità
                val sim = similarity(it.name, searchedText)
                if (sim > maxSF) maxSF = sim
            }
            foods.forEach {
                val sim = similarity(it.name, searchedText)
                if (sim > maxSF / 2) {
                    searchedList.add(it)
                }
            }
            if (searchedText != "") {
                val sortedFoods = searchedList.sortedBy { it.name }
                foodRecycler.adapter = FoodAdapter(sortedFoods as ArrayList<Food>, this)
            } else {
                foodRecycler.adapter = FoodAdapter(foods, this)
            }
        }
        return view
    }

    private fun similarity(s1: String, s2: String): Double {
        var longer = s1
        var shorter = s2
        if (s1.length < s2.length) { // longer should always have greater length
            longer = s2
            shorter = s1
        }
        val longerLength = longer.length
        return if (longerLength == 0) {
            1.0 /* both strings are zero length */
        } else (longerLength - editDistance(longer, shorter)) / longerLength.toDouble()
    }

    private fun editDistance(s1: String, s2: String): Int {
        var s1_ = s1
        var s2_ = s2
        s1_ = s1_.lowercase(Locale.ROOT)
        s2_ = s2_.lowercase(Locale.ROOT)
        val costs = IntArray(s2_.length + 1)
        for (i in 0..s1_.length) {
            var lastValue = i
            for (j in 0..s2_.length) {
                if (i == 0) costs[j] = j else {
                    if (j > 0) {
                        var newValue = costs[j - 1]
                        if (s1_[i - 1] != s2_[j - 1]) newValue = Math.min(
                            Math.min(newValue, lastValue),
                            costs[j]
                        ) + 1
                        costs[j - 1] = lastValue
                        lastValue = newValue
                    }
                }
            }
            if (i > 0) costs[s2_.length] = lastValue
        }
        return costs[s2_.length]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        viewModel.foods.observe(viewLifecycleOwner, { mutableList ->
            foods = mutableList as ArrayList<Food>
            foodRecycler.adapter =FoodAdapter(foods, this)
        })

    }

    override fun onFoodListener(food: Food, position: Int, command: Int) {
        when (command) {
            MAIN -> {
                viewModel.setFoodToAdd(food)
                val text = "${food.name} riportato"
                Toast.makeText(
                    requireContext(),
                    text,
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.mainFragment)
            }
            DELETE -> {
                viewModel.deleteFood(food)
            }
            EDIT -> {
                val dialog = BottomSheetDialog(requireContext())
                val bottomSheet =
                    layoutInflater.inflate(R.layout.dialog_bottom_addfood, containerForFunction, false)
                dialog.setContentView(bottomSheet)

                val dialogTitle = dialog.findViewById<TextView>(R.id.dialogTitle)
                dialogTitle?.text = getString(R.string.edit_food)

                val nameText = dialog.findViewById<TextInputEditText>(R.id.nameText)
                nameText?.setText(food.name)
                val carboText = dialog.findViewById<TextInputEditText>(R.id.carboText)
                carboText?.setText(food.carbo.toString())
                val btn100 = dialog.findViewById<TextView>(R.id.switchA)
                val btnPiece = dialog.findViewById<TextView>(R.id.switchB)

                btn100?.setOnClickListener {
                    val nameTmp = nameText?.text.toString()
                    val carboTmp = carboText?.text.toString()
                    if (nameTmp != "" && carboTmp != "") {
                        viewModel.deleteFood(food)
                        viewModel.insertFood(Food(0, nameTmp, carboTmp.toInt(), false))
                        dialog.dismiss()
                    }
                }
                btnPiece?.setOnClickListener {
                    val nameTmp = nameText?.text.toString()
                    val carboTmp = carboText?.text.toString()
                    if (nameTmp != "" && carboTmp != "") {
                        viewModel.deleteFood(food)
                        viewModel.insertFood(Food(0, nameTmp, carboTmp.toInt(), true))
                        dialog.dismiss()
                    }
                }
                dialog.show()

            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "Errore riscontrato",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

}