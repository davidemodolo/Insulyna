package com.davidemodolo.insulyna

import androidx.lifecycle.ViewModel
import com.davidemodolo.insulyna.food.Food


class AppViewModel : ViewModel() {
    private var foodToEdit: Food? = null
    fun setFoodToEdit(food: Food) {
        foodToEdit = food
    }

    fun getFoodToEdit(): Food {
        return foodToEdit!!
    }


}