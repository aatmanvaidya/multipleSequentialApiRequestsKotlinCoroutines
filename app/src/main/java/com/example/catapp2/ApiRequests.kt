package com.example.catapp2

import com.example.catapp2.api.CatJson
import retrofit2.Response
import retrofit2.http.GET

interface ApiRequests {
    @GET("/facts/random")
    suspend fun getCatFacts(): Response<CatJson>
}