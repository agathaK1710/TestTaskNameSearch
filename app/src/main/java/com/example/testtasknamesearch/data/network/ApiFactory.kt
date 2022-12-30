package com.example.testtasknamesearch.data.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(context: Context) {
    private val BASE_URL = "https://api.agify.io/"

    private val SIZE_OF_CACHE = (10 * 1024 * 1024).toLong()
    private val cache = Cache(context.cacheDir, SIZE_OF_CACHE)

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(CacheInterceptor())
        .addInterceptor(ForceCacheInterceptor())
        .cache(cache)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}