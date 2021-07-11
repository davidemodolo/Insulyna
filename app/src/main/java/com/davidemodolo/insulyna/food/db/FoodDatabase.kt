package com.davidemodolo.insulyna.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*classe per il database locale di cibi*/

@Database(entities = [Food::class], version = 1, exportSchema = false)

abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDAO(): FoodDAO

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDatabase::class.java,
                        "foods.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}