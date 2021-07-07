package com.davidemodolo.insulyna

import androidx.lifecycle.ViewModel
import com.davidemodolo.insulyna.food.Food


class AppViewModel : ViewModel() {
    private var foodToEdit: Food? = null
    fun setFoodToAdd(food: Food) {
        foodToEdit = food
    }

    fun getFoodToAdd(): Food? {
        return foodToEdit
    }


}