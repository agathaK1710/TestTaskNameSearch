package com.example.testtasknamesearch.data

import com.example.testtasknamesearch.data.database.FavoriteName
import com.example.testtasknamesearch.domain.FavoriteNameInfo

class NameAgeMapper {

    fun mapFavoriteNameToInfo(nameAge: FavoriteName) = FavoriteNameInfo(
        name = nameAge.name,
        age = nameAge.age
    )

    fun mapInfoToFavoriteName(nameAge: FavoriteNameInfo) = FavoriteName(
        name = nameAge.name,
        age = nameAge.age
    )
}