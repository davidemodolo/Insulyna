package com.davidemodolo.insulyna

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.davidemodolo.insulyna.food.db.Food
import com.davidemodolo.insulyna.food.db.FoodRepository


class AppViewModel(val app: Application) : AndroidViewModel(app) {
    private var foodToEdit: Food? = null
    private var carbo = 0.0F
    private val foodsdb = FoodRepository(app)
    var foods = MutableLiveData<List<Food>>()

    init{
        foodsdb.getAll()
        foods = foodsdb.foodData
    }

    fun insertFood(food: Food) {
        foodsdb.insertFood(food)
    }
    /*rimozione pasto sia dal DB locale che da Firebase*/
    fun deleteFood(food: Food) {
        foodsdb.deleteFood(food)
    }


    fun setFoodToAdd(food: Food) {
        foodToEdit = food
    }

    fun getFoodToAdd(): Food? {
        return foodToEdit
    }

    fun resetFoodToAdd() {
        foodToEdit = null
    }

    fun getCarbo(): Float {
        return carbo
    }

    fun setCarbo(newCarbo: Float) {
        carbo = newCarbo
    }


}