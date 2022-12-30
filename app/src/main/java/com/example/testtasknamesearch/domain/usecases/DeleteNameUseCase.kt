package com.example.testtasknamesearch.domain.usecases

import com.example.testtasknamesearch.domain.FavoriteNameInfo
import com.example.testtasknamesearch.domain.NameAgeRepository

class DeleteNameUseCase(
    private val repository: NameAgeRepository
) {
    suspend operator fun invoke(name: String) = repository.deleteFromFavorites(name)
}