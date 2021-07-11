package com.davidemodolo.insulyna.food.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var carbo: Int,
    var piece: Boolean
)
