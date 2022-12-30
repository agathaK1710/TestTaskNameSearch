package com.example.testtasknamesearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FavoriteName::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DATABASE_NAME = "prediction.db"

        fun getInstance(context: Context): AppDatabase {
            db?.let {
                return it
            }
            val instance =
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            db = instance
            return instance
        }
    }

    abstract fun favoriteNameDao(): FavoriteNameDao
}