package com.davidemodolo.insulyna

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.davidemodolo.insulyna.food.db.Food
import com.davidemodolo.insulyna.food.db.FoodRepository


class AppViewModel(val app: Application) : AndroidViewModel(app) {
    private var foodToAdd = Pair(0, 0.0F) //<Int, Float>
    private var carbo = 0.0F
    private val foodsdb = FoodRepository(app)
    var foods = MutableLiveData<List<Food>>()

    init {
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

    fun setFoodToAdd(carbo: Int, eaten: Float) {
        foodToAdd = Pair(carbo, eaten)
    }

    fun getFoodToAdd(): Pair<Int, Float> {
        return foodToAdd
    }

    fun resetFoodToAdd() {
        foodToAdd = Pair(0, 0.0F)
    }

    fun getCarbo(): Float {
        return carbo
    }

    fun setCarbo(newCarbo: Float) {
        carbo = newCarbo
    }


}