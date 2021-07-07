package com.davidemodolo.insulyna.food

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.davidemodolo.insulyna.AppViewModel
import com.davidemodolo.insulyna.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodFragment : Fragment(), FoodAdapter.FoodListener {

    private lateinit var viewModel: AppViewModel
    private val MAIN = 1
    private val EDIT = 2
    private val DELETE = 3

    private val foods = ArrayList<Food>()
    private lateinit var foodRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_food, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
        for (i in 1..10){
            foods.add(Food("Cibo $i", i, i%2==0))
        }
        foodRecycler = view.findViewById(R.id.recyclerFood)
        foodRecycler.adapter = FoodAdapter(foods, this)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheet = layoutInflater.inflate(R.layout.dialog_bottom_addfood, container, false)
            dialog.setContentView(bottomSheet)
            dialog.show()
        }
        return view
    }

    override fun onFoodListener(food: Food, position: Int, command: Int) {
        when(command){
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
            DELETE ->{
                foods.remove(food)
                foodRecycler.adapter = FoodAdapter(foods, this)
            }
            EDIT ->{

            }
            else ->{
                Toast.makeText(
                    requireContext(),
                    "Errore riscontrato",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

}