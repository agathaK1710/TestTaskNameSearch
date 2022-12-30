package com.example.testtasknamesearch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_names")
data class FavoriteName(
    @PrimaryKey
    val name: String,
    val age: Int
)