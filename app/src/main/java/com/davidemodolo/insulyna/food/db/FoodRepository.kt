package com.davidemodolo.insulyna.food.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodRepository(app: Application) {
    /*lista di pasti che verrà presa dal database*/
    var foodData = MutableLiveData<List<Food>>()
    /*oggetto per comunicare con il database locale*/
    private val foodDAO = FoodDatabase.getDatabase(app).foodDAO()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data: List<Food> = foodDAO.getAll()
            /*se il database locale è vuoto controllo se ci sono elementi online*/
            if (data.isNullOrEmpty()) {
                //foodData.postValue(foodDAO.getAll())
            } else {
                /*se il database locale di pasti è già popolato recupero le entry*/
                foodData.postValue(data)
            }
        }
    }

    /*recupero tutti i pasti nel database attraverso una coroutine per non appesantire il thread principale*/
    fun getAll() {
        CoroutineScope(Dispatchers.IO).launch {
            foodData.postValue(foodDAO.getAll())
        }
    }

    /*inserisco un nuovo pasto nel database attraverso una coroutine per non appesantire il thread principale*/
    fun insertFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDAO.insertFood(food)
            foodData.postValue(foodDAO.getAll())
        }
    }

    /*elimino un pasto dal database attraverso una coroutine per non appesantire il thread principale*/
    fun deleteFood(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            foodDAO.deleteFood(food)
            foodData.postValue(foodDAO.getAll())
        }
    }

}