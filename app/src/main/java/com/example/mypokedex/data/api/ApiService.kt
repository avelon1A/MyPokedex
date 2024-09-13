package com.example.mypokedex.data.api

import com.example.mypokedex.data.model.response.EvolutionChainResponse
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.data.model.response.PokemonDetails
import com.example.mypokedex.data.model.response.PokemonSpeciesResponse
import com.example.mypokedex.data.model.response.PokemonSpeciesResponseText
import com.example.mypokedex.data.model.response.TypeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 40,
        @Query("offset") offset: Int = 0
    ): Pokemon

    @GET("pokemon/{name}")
    suspend fun getPokemonData(
        @Path("name") name: Any

    ):PokemonDetails

    @GET("type/{type}")
    suspend fun getPokemonTypeByName(
        @Path("type") type: String
    ): TypeResponse

    @GET("pokemon-species/{name}")
    suspend fun getGenderRate(
        @Path("name") name: String

    ): PokemonSpeciesResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonSpeciesResponseText

    @GET("evolution-chain/{id}")
   suspend fun getEvolutionChain(
        @Path("id") id: Int): Response<EvolutionChainResponse>



}