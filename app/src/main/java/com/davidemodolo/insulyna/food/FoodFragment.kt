package com.davidemodolo.insulyna.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.davidemodolo.insulyna.AppViewModel
import com.davidemodolo.insulyna.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodFragment : Fragment(), FoodAdapter.FoodListener {

    private lateinit var viewModel: AppViewModel


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
        val foods = ArrayList<Food>()
        for (i in 1..10){
            foods.add(Food("$i", i.toFloat(), i%2==0))
        }
        val foodRecycler = view.findViewById<RecyclerView>(R.id.recyclerFood)
        foodRecycler.adapter = FoodAdapter(foods, this)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            /*val bsd = BottomSheetDialog(requireContext(), R.style.BottomSheedDialogTheme)
            val bsv = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_addfood, container)
            bsd.show()*/
        }
        return view
    }

    override fun onFoodListener(food: Food, position: Int, longPress: Boolean) {
        viewModel.setFoodToEdit(food)
    }

}