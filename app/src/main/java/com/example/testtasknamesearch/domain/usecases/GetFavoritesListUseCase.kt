package com.example.testtasknamesearch.domain.usecases

import com.example.testtasknamesearch.domain.NameAgeRepository

class GetFavoritesListUseCase(
    private val repository: NameAgeRepository
) {
    operator fun invoke() = repository.getFavouriteNames()
}