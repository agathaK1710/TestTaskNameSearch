package com.example.testtasknamesearch.data.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.testtasknamesearch.data.NameAgeMapper
import com.example.testtasknamesearch.data.database.AppDatabase
import com.example.testtasknamesearch.data.network.ApiFactory
import com.example.testtasknamesearch.domain.FavoriteNameInfo
import com.example.testtasknamesearch.domain.NameAgeRepository
import retrofit2.HttpException
import java.lang.Exception

class NameAgeRepositoryImpl(
    application: Application
) : NameAgeRepository {

    private val apiService = ApiFactory(application).apiService
    private val mapper = NameAgeMapper()
    private val favNameDao = AppDatabase.getInstance(application).favoriteNameDao()

    override suspend fun addToFavorite(name: FavoriteNameInfo) {
        favNameDao.addNameToFavorites(mapper.mapInfoToFavoriteName(name))
    }

    override suspend fun deleteFromFavorites(name: String) {
        favNameDao.deleteNameFromFavorites(name)
    }

    override suspend fun getAge(name: String): String {
        return try {
            apiService.getAgeByName(name).age.toString()
        } catch (e: Exception) {
            (e as? HttpException)?.response()?.message().toString()
        }
    }

    override fun getFavouriteNames(): LiveData<List<FavoriteNameInfo>> {
        return Transformations.map(favNameDao.getFavoritesNames()) { list ->
            list.map {
                mapper.mapFavoriteNameToInfo(it)
            }
        }
    }
}