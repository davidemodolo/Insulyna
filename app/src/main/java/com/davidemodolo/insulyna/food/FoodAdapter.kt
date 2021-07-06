package com.davidemodolo.insulyna.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidemodolo.insulyna.R

class FoodAdapter(private val foodList: ArrayList<Food>, private val foodListener: FoodListener): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val carbo: TextView = itemView.findViewById(R.id.foodCarbo)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        with(holder) {
            name.text = food.name
            val carboString = if(food.piece)
                String.format("%.0f",food.carbo)+ "g/pezzo"
            else
                String.format("%.0f",food.carbo)+ "g/100g"

            carbo.text = carboString

            holder.itemView.setOnClickListener {
                foodListener.onFoodListener(food, holder.layoutPosition, false)
            }
            holder.itemView.setOnLongClickListener {
                foodListener.onFoodListener(food, holder.layoutPosition, true)
                true
            }

        }


    }

    interface FoodListener {
        fun onFoodListener(food: Food, position: Int, longPress: Boolean)
    }
}

