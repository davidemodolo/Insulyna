package com.davidemodolo.insulyna.food.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*query per il database locale meals*/
@Dao
interface FoodDAO {

    @Query("SELECT * FROM foods ORDER BY name")
    suspend fun getAll(): List<Food>

    @Insert
    suspend fun insertFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()
}