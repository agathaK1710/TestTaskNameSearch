package com.example.testtasknamesearch.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNameToFavorites(name: FavoriteName)

    @Query("DELETE FROM favorites_names WHERE name=:name")
    suspend fun deleteNameFromFavorites(name: String)

    @Query("SELECT * FROM favorites_names")
    fun getFavoritesNames(): LiveData<List<FavoriteName>>
}