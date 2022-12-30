package com.example.testtasknamesearch.data.network

import com.google.gson.annotations.SerializedName

data class NameAgeDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int
)