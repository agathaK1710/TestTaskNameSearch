package com.example.testtasknamesearch.presentation.favotitesPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testtasknamesearch.data.repository.NameAgeRepositoryImpl
import com.example.testtasknamesearch.domain.usecases.DeleteNameUseCase
import com.example.testtasknamesearch.domain.usecases.GetFavoritesListUseCase

class FavoritePageViewModel(application: Application): AndroidViewModel(application) {
    private val repositoryImpl = NameAgeRepositoryImpl(application)

    private val getFavouritesListUseCase = GetFavoritesListUseCase(repositoryImpl)
    private val deleteNameUseCase = DeleteNameUseCase(repositoryImpl)

    val favoritesList = getFavouritesListUseCase()

    suspend fun deleteName(name: String) = deleteNameUseCase(name)
}