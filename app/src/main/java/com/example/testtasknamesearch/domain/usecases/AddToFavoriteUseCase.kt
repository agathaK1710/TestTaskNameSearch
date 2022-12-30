package com.example.testtasknamesearch.domain.usecases

import com.example.testtasknamesearch.domain.FavoriteNameInfo
import com.example.testtasknamesearch.domain.NameAgeRepository

class AddToFavoriteUseCase(
    private val repository: NameAgeRepository
) {
    suspend operator fun invoke(nameAge: FavoriteNameInfo) = repository.addToFavorite(nameAge)
}