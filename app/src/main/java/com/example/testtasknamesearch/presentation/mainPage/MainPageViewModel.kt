package com.example.testtasknamesearch.presentation.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testtasknamesearch.data.repository.NameAgeRepositoryImpl
import com.example.testtasknamesearch.domain.FavoriteNameInfo
import com.example.testtasknamesearch.domain.usecases.AddToFavoriteUseCase
import com.example.testtasknamesearch.domain.usecases.GetAgeUseCase

class MainPageViewModel(application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = NameAgeRepositoryImpl(application)

    private val getAgeUseCase = GetAgeUseCase(repositoryImpl)
    private val addToFavoritesUseCase = AddToFavoriteUseCase(repositoryImpl)

    suspend fun getAgeByName(name: String) = getAgeUseCase(name)
    suspend fun addToFavorite(nameAge: FavoriteNameInfo) = addToFavoritesUseCase(nameAge)
}