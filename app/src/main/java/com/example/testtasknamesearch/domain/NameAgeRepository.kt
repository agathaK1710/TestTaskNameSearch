package com.example.testtasknamesearch.domain

import androidx.lifecycle.LiveData

interface NameAgeRepository {
    suspend fun addToFavorite(name: FavoriteNameInfo)
    suspend fun deleteFromFavorites(name: String)
    suspend fun getAge(name: String): String
    fun getFavouriteNames(): LiveData<List<FavoriteNameInfo>>
}