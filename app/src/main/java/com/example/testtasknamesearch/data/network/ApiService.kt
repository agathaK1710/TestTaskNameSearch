package com.example.testtasknamesearch.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    suspend fun getAgeByName(
        @Query(QUERY_PARAM_NAME) name: String
    ): NameAgeDto

    companion object{
        private const val QUERY_PARAM_NAME = "name"
    }
}