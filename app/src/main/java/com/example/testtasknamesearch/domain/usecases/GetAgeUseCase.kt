package com.example.testtasknamesearch.domain.usecases

import com.example.testtasknamesearch.domain.NameAgeRepository

class GetAgeUseCase(
    private val repository: NameAgeRepository
) {
    suspend operator fun invoke(name: String) = repository.getAge(name)
}