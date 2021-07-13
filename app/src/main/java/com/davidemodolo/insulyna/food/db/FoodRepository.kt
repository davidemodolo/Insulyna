package com.davidemodolo.insulyna.food.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodRepository(app: Application) {
    /*food list from db*/
    var foodData = MutableLiveData<List<Food>>()

    private val foodDAO = FoodDatabase.getDatabase(app).foodDAO()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data: List<Food> = foodDAO.getAll()
            if (data.isNullOrEmpty()) {
                //foodData.postValue(foodDAO.getAll())
            } else {
                foodData.postValue(data)
            }
        }
    }

    fun getAll() {
        CoroutineScope(Dispatchers.IO).launch {
            foodData.postValue(foodDAO.getAll())
        }
    }

    fun insertFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDAO.insertFood(food)
            foodData.postValue(foodDAO.getAll())
        }
    }

    fun deleteFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDAO.deleteFood(food)
            foodData.postValue(foodDAO.getAll())
        }
    }

}