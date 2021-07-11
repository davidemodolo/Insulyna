package com.davidemodolo.insulyna

import androidx.lifecycle.ViewModel
import com.davidemodolo.insulyna.food.db.Food


class AppViewModel : ViewModel() {
    private var foodToEdit: Food? = null
    private var carbo = 0.0F
    fun setFoodToAdd(food: Food) {
        foodToEdit = food
    }

    fun getFoodToAdd(): Food? {
        return foodToEdit
    }

    fun resetFoodToAdd(){
        foodToEdit = null
    }

    fun getCarbo(): Float{
        return carbo
    }
    fun setCarbo(newCarbo: Float){
        carbo = newCarbo
    }


}