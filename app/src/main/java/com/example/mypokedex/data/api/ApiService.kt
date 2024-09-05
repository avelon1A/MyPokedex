package com.example.mypokedex.data.api

import com.example.mypokedex.data.model.response.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 40,
        @Query("offset") offset: Int = 0
    ): Pokemon
}