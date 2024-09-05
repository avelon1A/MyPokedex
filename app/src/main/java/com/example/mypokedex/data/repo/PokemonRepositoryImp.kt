package com.example.mypokedex.data.repo

import com.example.mypokedex.data.api.ApiService
import com.example.mypokedex.data.model.response.Pokemon
import com.example.mypokedex.domain.repo.PokemonRepository

class PokemonRepositoryImp(private val apiService: ApiService): PokemonRepository {

    override suspend fun getPokemonList(page: Int, limit: Int): Pokemon{
            return apiService.getPokemonList(page,limit)
    }

}