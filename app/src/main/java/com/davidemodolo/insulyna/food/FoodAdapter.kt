package com.davidemodolo.insulyna.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.davidemodolo.insulyna.R

class FoodAdapter(private val foodList: ArrayList<Food>, private val foodListener: FoodListener) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val MAIN = 1
    private val EDIT = 2
    private val DELETE = 3

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val carbo: TextView = itemView.findViewById(R.id.foodCarbo)
        val longPressLayout: ConstraintLayout = itemView.findViewById(R.id.longPressLayout)
        val btnEdit: TextView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: TextView = itemView.findViewById(R.id.btnDelete)

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
            val carboString = if (food.piece)
                food.carbo.toString() + "g/pezzo"
            else
                food.carbo.toString() + "g/100g"

            carbo.text = carboString
            btnEdit.setOnClickListener {
                foodListener.onFoodListener(food, holder.layoutPosition, EDIT)
            }

            btnDelete.setOnClickListener {
                foodListener.onFoodListener(food, holder.layoutPosition, DELETE)
            }

            holder.itemView.setOnClickListener {
                foodListener.onFoodListener(food, holder.layoutPosition, MAIN)
            }
            holder.itemView.setOnLongClickListener {
                longPressLayout.visibility = View.VISIBLE
                true
            }
        }
    }

    interface FoodListener {
        //command: 1 toMain, 2 Edit, 3 Delete
        fun onFoodListener(
            food: Food,
            position: Int,
            command: Int
        )
    }
}

